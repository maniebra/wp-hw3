import React, { useState } from 'react';
import Navbar from './views/components/navbar';
import Canvas from './views/components/canvas';
import Toolbar from './views/components/toolbar';
import Stats from './views/components/stats';
import { useCanvasVM } from './viewmodels/canvas-vm';
import { type ShapeType } from './models/shapes';

export default function APP() {
  const [tool, setTool] = useState<ShapeType | 'erase'>('circle');
  const vm = useCanvasVM();

  return (
    <div
      style={{
        height: '100vh',
        width: '100vw',
        background: '#111',
        color: '#fff',
        fontFamily: 'sans-serif',
        display: 'flex',
        gap: '1rem',
      }}
    >
      <div style={{ display: 'flex', flexDirection: 'column', width: '95vw', height: '97%' }}>
        <Navbar
          name={vm.drawingName}
          setName={vm.setDrawingName}
          onExport={vm.exportJSON}
          onImport={vm.importJSON}
        />
        <div style={{ display: 'flex', flex: 1 }}>
          <Canvas vm={vm} tool={tool} />
          <Toolbar
            tool={tool}
            setTool={setTool}
            onSave={vm.saveCurrent}
            onLoad={vm.loadFromStorage}
          />
        </div>
        <Stats vm={vm} />
      </div>
    </div>
  );
}
