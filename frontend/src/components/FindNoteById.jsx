import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useNotesStore } from "../store/useNotesStore";

function FindNoteById() {
  const { t } = useTranslation();
  const { fetchNoteById, clearFoundNote, foundNote, findLoading, findError, startEdit } =
    useNotesStore();

  const [idInput, setIdInput] = useState("");
  const [validationError, setValidationError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    setValidationError("");
    const parsed = Number(idInput);
    if (!Number.isInteger(parsed) || parsed <= 0) {
      setValidationError(t("invalidId"));
      return;
    }
    fetchNoteById(parsed);
  };

  const handleClose = () => {
    clearFoundNote();
    setIdInput("");
    setValidationError("");
  };

  const handleEdit = () => {
    if (foundNote) {
      startEdit(foundNote);
      handleClose();
    }
  };

  return (
    <section className="find-section card">
      <h2 className="section-title">{t("readByIdTitle")}</h2>
      <p className="section-hint">{t("readByIdHint")}</p>

      <form className="find-form" onSubmit={handleSubmit}>
        <label htmlFor="noteId">{t("noteIdLabel")}</label>
        <div className="find-row">
          <input
            id="noteId"
            name="noteId"
            type="number"
            min="1"
            step="1"
            value={idInput}
            onChange={(e) => setIdInput(e.target.value)}
            placeholder={t("noteIdPlaceholder")}
            required
          />
          <button className="btn btn-primary" type="submit" disabled={findLoading}>
            {findLoading ? t("loading") : t("readBtn")}
          </button>
        </div>
      </form>

      {validationError && (
        <p className="status error">{validationError}</p>
      )}
      {findError && (
        <p role="alert" className="status error">
          {t("readFailed")}: {findError}
        </p>
      )}

      {foundNote && (
        <div className="found-note note-item">
          <h3>{foundNote.title}</h3>
          <p>{foundNote.content}</p>
          <span className="note-id">ID: {foundNote.id}</span>
          <div className="actions">
            <button className="btn btn-muted" type="button" onClick={handleEdit}>
              {t("editBtn")}
            </button>
            <button className="btn btn-muted" type="button" onClick={handleClose}>
              {t("cancelBtn")}
            </button>
          </div>
        </div>
      )}
    </section>
  );
}

export default FindNoteById;
