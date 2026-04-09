import { create } from "zustand";
import {
  createNote,
  deleteNote,
  getNoteById,
  getNotes,
  updateNote,
} from "../api/notesApi";

export const useNotesStore = create((set, get) => ({
  notes: [],
  loading: false,
  saving: false,
  error: "",
  editingId: null,
  editPayload: null,

  currentPage: 0,
  pageSize: 5,
  totalPages: 0,
  totalElements: 0,

  foundNote: null,
  findLoading: false,
  findError: "",

  fetchNotes: async (page, size) => {
    const p = page ?? get().currentPage;
    const s = size ?? get().pageSize;
    set({ loading: true, error: "" });
    try {
      const pageData = await getNotes(p, s);
      set({
        notes: pageData?.content || [],
        currentPage: pageData?.number ?? 0,
        totalPages: pageData?.totalPages ?? 0,
        totalElements: pageData?.totalElements ?? 0,
        pageSize: s,
        loading: false,
      });
    } catch (err) {
      set({ error: err.message, loading: false });
    }
  },

  setPage: (page) => {
    get().fetchNotes(page);
  },

  setPageSize: (size) => {
    get().fetchNotes(0, size);
  },

  fetchNoteById: async (id) => {
    set({ findLoading: true, findError: "", foundNote: null });
    try {
      const note = await getNoteById(id);
      set({ foundNote: note, findLoading: false });
    } catch (err) {
      set({ findError: err.message, findLoading: false });
    }
  },

  clearFoundNote: () => {
    set({ foundNote: null, findError: "" });
  },

  addNote: async (payload) => {
    set({ error: "", saving: true });
    try {
      await createNote(payload);
      // Refetch to find the new note — go to last page
      await get().fetchNotes(0);
      const { totalPages } = get();
      if (totalPages > 1) {
        await get().fetchNotes(totalPages - 1);
      }
    } catch (err) {
      set({ error: err.message });
      throw err;
    } finally {
      set({ saving: false });
    }
  },

  saveNote: async (id, payload) => {
    set({ error: "", saving: true });
    try {
      await updateNote(id, payload);
      set({ editingId: null, editPayload: null });
      await get().fetchNotes();
    } catch (err) {
      set({ error: err.message });
      throw err;
    } finally {
      set({ saving: false });
    }
  },

  removeNote: async (id) => {
    set({ error: "" });
    try {
      await deleteNote(id);
      const { notes, currentPage } = get();
      if (notes.length === 1 && currentPage > 0) {
        await get().fetchNotes(currentPage - 1);
      } else {
        await get().fetchNotes();
      }
    } catch (err) {
      set({ error: err.message });
      throw err;
    }
  },

  startEdit: (note) => {
    set({
      editingId: note.id,
      editPayload: { title: note.title, content: note.content },
    });
  },

  clearEdit: () => {
    set({ editingId: null, editPayload: null });
  },
}));
