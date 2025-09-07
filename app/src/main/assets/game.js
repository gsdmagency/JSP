(() => {
  const canvas = document.getElementById('game');
  const ctx = canvas.getContext('2d');

  // Grid configuration
  const cols = 7; // width
  const rows = 9; // height
  const cell = 60; // px
  const boardPxW = cols * cell;
  const boardPxH = rows * cell;
  canvas.width = boardPxW;
  canvas.height = boardPxH;

  const Colors = {
    red: '#e63946',
    orange: '#f4a261',
    yellow: '#e9c46a',
    green: '#2a9d8f',
    blue: '#457b9d',
    purple: '#7b2cbf'
  };
  const colorKeys = Object.keys(Colors);

  function setCounter(colorKey, value) {
    const el = document.querySelector(`.counter[data-color="${colorKey}"] span`);
    if (el) el.textContent = String(value);
  }

  // Board occupancy: 0 = empty, 1 = solid
  const solid = Array.from({ length: rows }, () => Array(cols).fill(0));

  // Stars are collectible items placed on empty cells
  const stars = [];

  // Shapes: simple rectangles and plus as a set of occupied offsets
  const shapes = [];

  function addStar(x, y, color) {
    stars.push({ x, y, color });
  }

  function addShape(shape) {
    shapes.push(shape);
    occupy(shape, 1);
  }

  function occupy(shape, v) {
    for (const [ox, oy] of shape.blocks) {
      const gx = shape.x + ox;
      const gy = shape.y + oy;
      if (gy >= 0 && gy < rows && gx >= 0 && gx < cols) solid[gy][gx] = v;
    }
  }

  function makeRect(x, y, w, h, color) {
    const blocks = [];
    for (let iy = 0; iy < h; iy++) {
      for (let ix = 0; ix < w; ix++) blocks.push([ix, iy]);
    }
    return { type: 'rect', x, y, w, h, color, blocks };
  }

  function makePlus(x, y, color) {
    // 3x3 plus (cross)
    const blocks = [
      [1, 0],
      [0, 1], [1, 1], [2, 1],
      [1, 2]
    ];
    return { type: 'plus', x, y, w: 3, h: 3, color, blocks };
  }

  function canMove(shape, dx, dy) {
    for (const [ox, oy] of shape.blocks) {
      const nx = shape.x + ox + dx;
      const ny = shape.y + oy + dy;
      if (nx < 0 || nx >= cols || ny < 0 || ny >= rows) return false;
      // Temporarily free current cells during collision check
      const current = (shape.x + ox === nx - dx && shape.y + oy === ny - dy);
      if (!current && solid[ny][nx]) return false;
    }
    return true;
  }

  function moveBy(shape, dx, dy) {
    if (!canMove(shape, dx, dy)) return false;
    occupy(shape, 0);
    shape.x += dx;
    shape.y += dy;
    occupy(shape, 1);
    checkCaptures(shape);
    return true;
  }

  const captured = {
    red: 0, orange: 0, yellow: 0, green: 0, blue: 0, purple: 0
  };

  function checkCaptures(shape) {
    for (let i = stars.length - 1; i >= 0; i--) {
      const star = stars[i];
      // captured if star cell is now covered by any block of shape
      let covered = false;
      for (const [ox, oy] of shape.blocks) {
        if (shape.x + ox === star.x && shape.y + oy === star.y) {
          covered = true; break;
        }
      }
      if (covered) {
        if (shape.color === star.color) {
          stars.splice(i, 1);
          captured[star.color] += 1;
          setCounter(star.color, captured[star.color]);
        }
      }
    }
  }

  // Demo level: place a couple of shapes and stars
  function setupLevel() {
    // Reset
    for (let r = 0; r < rows; r++) solid[r].fill(0);
    stars.length = 0;
    shapes.length = 0;
    for (const k of colorKeys) setCounter(k, captured[k] = 0);

    addShape(makePlus(2, 2, 'blue'));
    addShape(makeRect(0, 0, 2, 3, 'green'));
    addShape(makeRect(4, 0, 3, 1, 'yellow'));
    addShape(makeRect(0, 6, 2, 3, 'purple'));
    addShape(makeRect(5, 5, 2, 2, 'orange'));
    addShape(makeRect(3, 7, 3, 1, 'red'));

    addStar(1, 1, 'green');
    addStar(4, 1, 'yellow');
    addStar(2, 5, 'blue');
    addStar(6, 8, 'red');
    addStar(5, 6, 'orange');
    addStar(0, 7, 'purple');
  }

  // Rendering
  function drawGrid() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = '#0f253f';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.strokeStyle = 'rgba(255,255,255,0.08)';
    for (let y = 0; y <= rows; y++) {
      ctx.beginPath();
      ctx.moveTo(0, y * cell);
      ctx.lineTo(boardPxW, y * cell);
      ctx.stroke();
    }
    for (let x = 0; x <= cols; x++) {
      ctx.beginPath();
      ctx.moveTo(x * cell, 0);
      ctx.lineTo(x * cell, boardPxH);
      ctx.stroke();
    }
  }

  function drawStars() {
    for (const s of stars) {
      const px = s.x * cell + cell / 2;
      const py = s.y * cell + cell / 2;
      ctx.fillStyle = Colors[s.color];
      starPath(px, py, 5, cell * 0.35, cell * 0.18);
      ctx.fill();
      ctx.strokeStyle = 'rgba(0,0,0,0.4)';
      ctx.lineWidth = 2;
      ctx.stroke();
    }
  }

  function starPath(x, y, points, outerRadius, innerRadius) {
    const step = Math.PI / points;
    ctx.beginPath();
    ctx.moveTo(x, y - outerRadius);
    for (let i = 0; i < points; i++) {
      ctx.lineTo(x + Math.cos(i * 2 * step) * outerRadius, y + Math.sin(i * 2 * step) * outerRadius);
      ctx.lineTo(x + Math.cos((i * 2 + 1) * step) * innerRadius, y + Math.sin((i * 2 + 1) * step) * innerRadius);
    }
    ctx.closePath();
  }

  function drawShapes() {
    for (const sh of shapes) {
      ctx.fillStyle = Colors[sh.color];
      ctx.strokeStyle = 'rgba(0,0,0,0.45)';
      for (const [ox, oy] of sh.blocks) {
        const x = (sh.x + ox) * cell;
        const y = (sh.y + oy) * cell;
        ctx.fillRect(x + 3, y + 3, cell - 6, cell - 6);
        ctx.strokeRect(x + 3, y + 3, cell - 6, cell - 6);
      }
    }
  }

  function render() {
    drawGrid();
    drawStars();
    drawShapes();
  }

  // Input handling: select with tap, move with arrow keys or swipe
  let selectedIndex = -1;

  canvas.addEventListener('pointerdown', ev => {
    const rect = canvas.getBoundingClientRect();
    const gx = Math.floor((ev.clientX - rect.left) / cell);
    const gy = Math.floor((ev.clientY - rect.top) / cell);
    selectedIndex = shapes.findIndex(sh => sh.blocks.some(([ox, oy]) => sh.x + ox === gx && sh.y + oy === gy));
    render();
    if (selectedIndex >= 0) highlightShape(shapes[selectedIndex]);
  });

  function highlightShape(sh) {
    ctx.save();
    ctx.strokeStyle = '#ffffff';
    ctx.lineWidth = 3;
    for (const [ox, oy] of sh.blocks) {
      const x = (sh.x + ox) * cell + 2;
      const y = (sh.y + oy) * cell + 2;
      ctx.strokeRect(x, y, cell - 4, cell - 4);
    }
    ctx.restore();
  }

  window.addEventListener('keydown', ev => {
    if (selectedIndex < 0) return;
    const sh = shapes[selectedIndex];
    let moved = false;
    if (ev.key === 'ArrowLeft') moved = moveBy(sh, -1, 0);
    else if (ev.key === 'ArrowRight') moved = moveBy(sh, 1, 0);
    else if (ev.key === 'ArrowUp') moved = moveBy(sh, 0, -1);
    else if (ev.key === 'ArrowDown') moved = moveBy(sh, 0, 1);
    if (moved) render();
  });

  // simple swipe handling
  let start = null;
  canvas.addEventListener('pointerdown', e => start = { x: e.clientX, y: e.clientY });
  canvas.addEventListener('pointerup', e => {
    if (selectedIndex < 0 || !start) return;
    const dx = e.clientX - start.x;
    const dy = e.clientY - start.y;
    const sh = shapes[selectedIndex];
    const ax = Math.abs(dx), ay = Math.abs(dy);
    if (Math.max(ax, ay) < 15) return; // tap only
    let moved = false;
    if (ax > ay) moved = moveBy(sh, dx > 0 ? 1 : -1, 0);
    else moved = moveBy(sh, 0, dy > 0 ? 1 : -1);
    start = null;
    if (moved) render();
  });

  setupLevel();
  render();
})();

