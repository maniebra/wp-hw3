import { useState, useEffect, useCallback } from 'react';
import { type Shape, type ShapeType } from '../models/shapes';
import { loadProject, saveProject } from '../utils/storage';
import { pointInShape } from '../utils/geometry';

const SIZE = 40;
const uid = () => `${Date.now().toString(36)}-${Math.random().toString(36).slice(2)}`;

interface ProjectJSON {
  name: string;
  shapes: Shape[];
}

export const useCanvasVM = () => {
  const [shapes, setShapes] = useState<Shape[]>([]);
  const [drawingName, setDrawingName] = useState<string>('Untitled');

  useEffect(() => {
    loadProject().then(({ name, shapes }) => {
      setDrawingName(name);
      setShapes(shapes);
    });
  }, []);

  const add = useCallback((type: ShapeType, x: number, y: number) => {
    setShapes((prev) => [...prev, { id: uid(), type, x, y, size: SIZE }]);
  }, []);

  const removeAt = useCallback((x: number, y: number) => {
    setShapes((prev) => prev.filter((s) => !pointInShape(s, x, y)));
  }, []);

  const updateShapePosition = useCallback((id: string, x: number, y: number) => {
    setShapes((prev) => prev.map((s) => (s.id === id ? { ...s, x, y } : s)));
  }, []);

  const saveCurrent = useCallback(async () => {
    await saveProject({ name: drawingName || 'Untitled', shapes });
  }, [drawingName, shapes]);

 const loadFromStorage = useCallback(async (id?: number) => {
    const { name, shapes: stored } = await loadProject(id);
    setDrawingName(name);
    setShapes(stored);
  }, []);

  const exportJSON = useCallback(() => {
    const blob = new Blob([JSON.stringify({ name: drawingName || 'Untitled', shapes }, null, 2)], {
      type: 'application/json',
    });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${(drawingName || 'drawing').replace(/\s+/g, '_')}.json`;
    a.click();
    URL.revokeObjectURL(url);
  }, [drawingName, shapes]);

  const importJSON = useCallback((file: File) => {
    const reader = new FileReader();
    reader.onload = () => {
      try {
        const json = JSON.parse(reader.result as string) as ProjectJSON;
        if (!Array.isArray(json.shapes)) throw new Error('Invalid JSON');
        setDrawingName(typeof json.name === 'string' ? json.name : 'Untitled');
        setShapes(json.shapes);
      } catch {
        alert('Failed to import JSON: Wrong format.');
      }
    };
    reader.readAsText(file);
  }, []);

  return {
    shapes,
    drawingName,
    setDrawingName,
    add,
    removeAt,
    updateShapePosition,
    saveCurrent,
    loadFromStorage,
    exportJSON,
    importJSON,
  } as const;
};
