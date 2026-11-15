const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = Number(process.env.APP_PORT) || 3000;
const PUBLIC_DIR = path.join(__dirname, 'public');
const DRAWABLE_DIR = path.join(__dirname, 'app', 'src', 'main', 'res', 'drawable');
const APK_PATHS = [
  path.join(__dirname, '.build-outputs', 'app-debug.apk'),
  path.join(__dirname, 'app', 'build', 'outputs', 'apk', 'debug', 'app-debug.apk')
];

const MIME_TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.js': 'application/javascript; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.jpg': 'image/jpeg',
  '.jpeg': 'image/jpeg',
  '.png': 'image/png',
  '.svg': 'image/svg+xml',
  '.ico': 'image/x-icon',
  '.ttf': 'font/ttf',
  '.apk': 'application/vnd.android.package-archive'
};

// Ensure public/images exists and copy generated drawable images if present
const publicImagesDir = path.join(PUBLIC_DIR, 'images');
try {
  if (!fs.existsSync(publicImagesDir)) {
    fs.mkdirSync(publicImagesDir, { recursive: true });
  }
  const imageFiles = [
    'img_hero_halong_bay.jpg',
    'img_hoian_lanterns.jpg',
    'img_hanoi_temple.jpg',
    'img_sapa_terraces.jpg',
    'img_launcher_compass.jpg'
  ];
  for (const img of imageFiles) {
    const src = path.join(DRAWABLE_DIR, img);
    const dest = path.join(publicImagesDir, img);
    if (fs.existsSync(src) && !fs.existsSync(dest)) {
      fs.copyFileSync(src, dest);
    }
  }
} catch (e) {
  // Ignore read-only filesystem in serverless environments
}

function handleRequest(req, res) {
  const parsedUrl = new URL(req.url || '/', `http://${req.headers.host || 'localhost'}`);
  let pathname = decodeURIComponent(parsedUrl.pathname);

  if (pathname === '/api/health') {
    res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
    res.end(JSON.stringify({
      status: 'ok',
      app: 'Journey AI Multi-Platform Distribution Hub',
      version: '2.5.0',
      apkAvailable: APK_PATHS.some((p) => fs.existsSync(p))
    }));
    return;
  }

  if (pathname === '/favicon.ico' || pathname === '/favicon.png') {
    const iconPath = path.join(PUBLIC_DIR, 'images', 'img_launcher_compass.jpg');
    if (fs.existsSync(iconPath)) {
      res.writeHead(200, { 'Content-Type': 'image/jpeg', 'Cache-Control': 'public, max-age=86400' });
      fs.createReadStream(iconPath).pipe(res);
      return;
    }
  }

  if (pathname === '/download/journey-ai.apk') {
    const apkFile = APK_PATHS.find((p) => fs.existsSync(p));
    if (apkFile) {
      const stat = fs.statSync(apkFile);
      res.writeHead(200, {
        'Content-Type': 'application/vnd.android.package-archive',
        'Content-Disposition': 'attachment; filename="Journey-AI-v2.5.apk"',
        'Content-Length': stat.size
      });
      fs.createReadStream(apkFile).pipe(res);
      return;
    }
    res.writeHead(404, { 'Content-Type': 'text/plain; charset=utf-8' });
    res.end('APK file is currently building. Please use Web/PWA install.');
    return;
  }

  if (pathname === '/' || pathname === '/index.html') {
    pathname = '/index.html';
  }

  let filePath = path.join(PUBLIC_DIR, pathname);
  if (!filePath.startsWith(PUBLIC_DIR)) {
    res.writeHead(403);
    res.end('Forbidden');
    return;
  }

  if (!fs.existsSync(filePath) && pathname.startsWith('/images/')) {
    const baseName = path.basename(pathname);
    const drawableFallback = path.join(DRAWABLE_DIR, baseName);
    if (fs.existsSync(drawableFallback)) {
      filePath = drawableFallback;
    }
  }

  fs.stat(filePath, (err, stats) => {
    if (err || !stats.isFile()) {
      const indexFile = path.join(PUBLIC_DIR, 'index.html');
      fs.readFile(indexFile, (readErr, content) => {
        if (readErr) {
          res.writeHead(404, { 'Content-Type': 'text/plain; charset=utf-8' });
          res.end('Not found');
          return;
        }
        res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
        res.end(content);
      });
      return;
    }

    const ext = path.extname(filePath).toLowerCase();
    const contentType = MIME_TYPES[ext] || 'application/octet-stream';
    res.writeHead(200, {
      'Content-Type': contentType,
      'Cache-Control': 'no-cache'
    });
    fs.createReadStream(filePath).pipe(res);
  });
}

// Export handler for Vercel Serverless Functions ([f] /)
module.exports = handleRequest;

// Start standalone HTTP server when running locally / in AI Studio container
if (!process.env.VERCEL) {
  const server = http.createServer(handleRequest);
  server.listen(PORT, '0.0.0.0', () => {
    console.log(`Journey AI Web & Distribution Hub listening on http://0.0.0.0:${PORT}`);
  });
}
