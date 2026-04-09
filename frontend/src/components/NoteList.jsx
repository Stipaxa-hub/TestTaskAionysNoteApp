import { useTranslation } from "react-i18next";
import { useNotesStore } from "../store/useNotesStore";
import NoteItem from "./NoteItem";
import Pagination from "./Pagination";

function NoteList() {
  const { t } = useTranslation();
  const { notes, loading, error } = useNotesStore();

  if (loading) {
    return <p className="status">{t("loading")}</p>;
  }

  if (error) {
    return (
      <p role="alert" className="status error">
        {t("errorPrefix")}: {error}
      </p>
    );
  }

  if (notes.length === 0) {
    return <p className="empty-state">{t("emptyNotes")}</p>;
  }

  return (
    <>
      <ul className="notes-list">
        {notes.map((note) => (
          <NoteItem key={note.id} note={note} />
        ))}
      </ul>
      <Pagination />
    </>
  );
}

export default NoteList;
