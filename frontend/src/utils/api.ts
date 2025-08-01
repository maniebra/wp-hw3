export const BASE_URL = 'http://localhost:8080';

export interface AuthRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}

let authToken: string | null = null;

const DEFAULT_CREDENTIALS: AuthRequest = {
  username: 'string',
  password: 'string',
};

const getToken = async (): Promise<string> => {
  if (authToken) return authToken;
  const res = await fetch(`${BASE_URL}/api/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(DEFAULT_CREDENTIALS),
  });
  if (!res.ok) throw new Error('Failed to authenticate');
  const data: AuthResponse = await res.json();
  authToken = data.token;
  return authToken;
};

const authorizedFetch = async (
  input: RequestInfo | URL,
  init: RequestInit = {}
): Promise<Response> => {
  const token = await getToken();
  const headers = {
    ...(init.headers || {}),
    Authorization: `Bearer ${token}`,
  } as Record<string, string>;
  return fetch(input, { ...init, headers });
};

export interface ShapeRequestDto {
  type: 'CIRCLE' | 'SQUARE' | 'TRIANGLE';
  x: number;
  y: number;
  size: number;
  paintingId: number;
}

export interface ShapeDto extends ShapeRequestDto {
  id: number;
}

export interface PaintingRequestDto {
  name: string;
}

export interface PaintingDto {
  id: number;
  name: string;
}

export const listPaintings = async (): Promise<PaintingDto[]> => {
  const res = await authorizedFetch(`${BASE_URL}/api/paintings`);
  if (!res.ok) throw new Error('Failed to load paintings');
  return res.json();
};

export const createPainting = async (req: PaintingRequestDto): Promise<PaintingDto> => {
  const res = await authorizedFetch(`${BASE_URL}/api/paintings`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(req),
  });
  if (!res.ok) throw new Error('Failed to create painting');
  return res.json();
};

export const deletePainting = async (id: number): Promise<void> => {
  await authorizedFetch(`${BASE_URL}/api/paintings/${id}`, { method: 'DELETE' });
};

export const listShapes = async (paintingId: number): Promise<ShapeDto[]> => {
  const res = await authorizedFetch(`${BASE_URL}/api/shapes?paintingId=${paintingId}`);
  if (!res.ok) throw new Error('Failed to load shapes');
  return res.json();
};

export const createShape = async (req: ShapeRequestDto): Promise<ShapeDto> => {
  const res = await authorizedFetch(`${BASE_URL}/api/shapes`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(req),
  });
  if (!res.ok) throw new Error('Failed to create shape');
  return res.json();
};

export const deleteShape = async (id: number): Promise<void> => {
  await authorizedFetch(`${BASE_URL}/api/shapes/${id}`, { method: 'DELETE' });
};