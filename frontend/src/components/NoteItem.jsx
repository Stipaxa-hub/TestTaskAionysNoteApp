import { useTranslation } from "react-i18next";
import { useNotesStore } from "../store/useNotesStore";

function NoteItem({ note }) {
  const { t } = useTranslation();
  const { startEdit, removeNote } = useNotesStore();

  return (
    <li className="note-item card">
      <h3>{note.title}</h3>
      <p>{note.content}</p>
      <div className="actions">
        <button
          className="btn btn-muted"
          type="button"
          onClick={() => startEdit(note)}
        >
          {t("editBtn")}
        </button>
        <button
          className="btn btn-danger"
          type="button"
          onClick={() => removeNote(note.id)}
        >
          {t("deleteBtn")}
        </button>
      </div>
    </li>
  );
}

export default NoteItem;
