import { type Shape, type ShapeType } from '../models/shapes';
import {
  listPaintings,
  createPainting,
  deletePainting,
  listShapes,
  createShape,
} from './api';

const toApiType = (t: ShapeType): 'CIRCLE' | 'SQUARE' | 'TRIANGLE' =>
  t.toUpperCase() as 'CIRCLE' | 'SQUARE' | 'TRIANGLE';
const fromApiType = (t: string): ShapeType => t.toLowerCase() as ShapeType;

interface ProjectJSON {
  name: string;
  shapes: Shape[];
}

export const loadProject = async (): Promise<ProjectJSON> => {
  const paintings = await listPaintings();
  if (paintings.length === 0) return { name: 'Untitled', shapes: [] };
  const painting = paintings[0];
  const shapesDto = await listShapes(painting.id);
  const shapes: Shape[] = shapesDto.map((s) => ({
    id: String(s.id),
    type: fromApiType(s.type),
    x: s.x,
    y: s.y,
    size: s.size,
  }));
  return { name: painting.name, shapes };
};

export const saveProject = async (proj: ProjectJSON): Promise<void> => {
  const paintings = await listPaintings();
  for (const p of paintings) await deletePainting(p.id);
  const painting = await createPainting({ name: proj.name });
  for (const s of proj.shapes) {
    await createShape({
      type: toApiType(s.type),
      x: s.x,
      y: s.y,
      size: s.size,
      paintingId: painting.id,
    });
  }
};