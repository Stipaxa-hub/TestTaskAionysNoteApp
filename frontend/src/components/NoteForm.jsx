import { useEffect, useMemo, useState } from "react";
import { useTranslation } from "react-i18next";
import { useNotesStore } from "../store/useNotesStore";

const emptyForm = { title: "", content: "" };

function NoteForm() {
  const { t } = useTranslation();
  const { addNote, saveNote, fetchNotes, editingId, editPayload, clearEdit, saving } =
    useNotesStore();

  const [form, setForm] = useState(emptyForm);

  useEffect(() => {
    if (editPayload) {
      setForm({ title: editPayload.title, content: editPayload.content });
    } else {
      setForm(emptyForm);
    }
  }, [editPayload]);

  const submitLabel = useMemo(
    () => (editingId ? t("updateBtn") : t("createBtn")),
    [editingId, t]
  );

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!form.title.trim() || !form.content.trim()) return;

    const payload = {
      title: form.title.trim(),
      content: form.content.trim(),
    };

    if (editingId) {
      await saveNote(editingId, payload);
    } else {
      await addNote(payload);
    }

    setForm(emptyForm);
  };

  const cancelEdit = () => {
    clearEdit();
    setForm(emptyForm);
  };

  return (
    <>
      <h2 className="section-title">{t("createSectionTitle")}</h2>
      <form className="note-form card" onSubmit={handleSubmit}>
      <label htmlFor="title">{t("titleLabel")}</label>
      <input
        id="title"
        name="title"
        value={form.title}
        onChange={(e) => setForm({ ...form, title: e.target.value })}
        placeholder={t("titlePlaceholder")}
        required
      />

      <label htmlFor="content">{t("contentLabel")}</label>
      <textarea
        id="content"
        name="content"
        value={form.content}
        onChange={(e) => setForm({ ...form, content: e.target.value })}
        placeholder={t("contentPlaceholder")}
        required
      />

      <div className="actions">
        <button className="btn btn-primary" type="submit" disabled={saving}>
          {saving ? t("loading") : submitLabel}
        </button>
        {editingId && (
          <button className="btn btn-muted" type="button" onClick={cancelEdit}>
            {t("cancelBtn")}
          </button>
        )}
        <button className="btn btn-muted" type="button" onClick={() => fetchNotes()}>
          {t("refreshBtn")}
        </button>
      </div>
      </form>
    </>
  );
}

export default NoteForm;
