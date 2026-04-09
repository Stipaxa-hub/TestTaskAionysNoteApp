const API_BASE_URL = import.meta.env.VITE_API_URL || "";

async function request(url, options = {}) {
  const response = await fetch(url, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
    ...options,
  });

  if (response.status === 204) {
    return null;
  }

  const data = await response.json().catch(() => null);
  if (!response.ok) {
    const message = data?.message || `Request failed (${response.status})`;
    throw new Error(message);
  }

  return data?.data;
}

export function getNotes(page = 0, size = 10) {
  return request(`${API_BASE_URL}/notes?page=${page}&size=${size}`);
}

export function getNoteById(id) {
  return request(`${API_BASE_URL}/notes/${id}`);
}

export function createNote(payload) {
  return request(`${API_BASE_URL}/notes`, {
    method: "POST",
    body: JSON.stringify(payload),
  });
}

export function updateNote(id, payload) {
  return request(`${API_BASE_URL}/notes/${id}`, {
    method: "PUT",
    body: JSON.stringify(payload),
  });
}

export function deleteNote(id) {
  return request(`${API_BASE_URL}/notes/${id}`, {
    method: "DELETE",
  });
}
