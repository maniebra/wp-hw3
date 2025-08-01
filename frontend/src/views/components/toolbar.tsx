import React, { useEffect, useState } from 'react';
import { Circle as CircleIcon, Square as SquareIcon, Triangle as TriangleIcon, SaveIcon, UploadIcon, Eraser } from 'lucide-react';
import { type ShapeType } from '../../models/shapes';
import { listProjects, removeProject } from '../../utils/storage';
import { type PaintingDto } from '../../utils/api';

interface Props {
  tool: ShapeType | 'erase';
  setTool: (t: ShapeType | 'erase') => void;
  onSave: () => void;
  onLoad: (id?: number) => void;
}

export default function Toolbar({ tool, setTool, onSave, onLoad }: Props) {
    const [open, setOpen] = useState(false);
  const [paintings, setPaintings] = useState<PaintingDto[]>([]);

  useEffect(() => {
    if (open) listProjects().then(setPaintings);
  }, [open]);

  const load = (id: number) => {
    onLoad(id);
    setOpen(false);
  };

  const del = async (id: number) => {
    await removeProject(id);
    setPaintings((prev) => prev.filter((p) => p.id !== id));
  };
  const btn = (t?: string) => ({
    padding: '0.5rem 0.75rem',
    border: t ? (tool === t ? '2px solid #00aaff' : '1px solid #555') : '1px solid #555',
    background: 'transparent',
    color: '#fff',
    display: 'flex',
    flexDirection: 'column' as const,
    alignItems: 'center',
    gap: '0.25rem',
    cursor: 'pointer',
    width: '80%',
  });

  const label: React.CSSProperties = { fontSize: '0.7rem' };

  return (
    <aside
      style={{
        backgroundColor: '#222',
        borderRadius: "1rem",
        width: '100px',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <button style={btn('circle')} onClick={() => setTool('circle')}>
        <CircleIcon size={16} />
        <span style={label}>Circle</span>
      </button>
      <button style={btn('square')} onClick={() => setTool('square')}>
        <SquareIcon size={16} />
        <span style={label}>Square</span>
      </button>
      <button style={btn('triangle')} onClick={() => setTool('triangle')}>
        <TriangleIcon size={16} />
        <span style={label}>Triangle</span>
      </button>
      <button style={btn('erase')} onClick={() => setTool('erase')}>
        <Eraser size={16} />
        <span style={label}>Erase</span>
      </button>

      <div style={{ width: '70%', borderTop: '1px solid #555', margin: '0.5rem 0' }} />

      <button style={btn()} onClick={onSave}>
        <SaveIcon size={20} />
        <span style={label}>Save</span>
      </button>
      <button style={btn()} onClick={() => setOpen(true)}>
        <UploadIcon size={20} />
        <span style={label}>Load</span>
      </button>
            {open && (
        <div
          style={{
            position: 'absolute',
            left: '110%',
            background: '#333',
            padding: '0.5rem',
            border: '1px solid #555',
            borderRadius: '0.5rem',
          }}
        >
          {paintings.map((p) => (
            <div
              key={p.id}
              style={{ display: 'flex', justifyContent: 'space-between', gap: '0.5rem', marginBottom: '0.25rem' }}
            >
              <span>{p.name}</span>
              <div style={{ display: 'flex', gap: '0.25rem' }}>
                <button onClick={() => load(p.id)}>Load</button>
                <button onClick={() => del(p.id)}>Del</button>
              </div>
            </div>
          ))}
          <button onClick={() => setOpen(false)}>Close</button>
        </div>
      )}
    </aside>
  );
}
