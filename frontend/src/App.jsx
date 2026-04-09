import { useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useNotesStore } from "./store/useNotesStore";
import Header from "./components/Header";
import FindNoteById from "./components/FindNoteById";
import NoteForm from "./components/NoteForm";
import NoteList from "./components/NoteList";

function App() {
  const { t } = useTranslation();
  const { totalElements, fetchNotes, editingId } = useNotesStore();

  useEffect(() => {
    fetchNotes();
  }, [fetchNotes]);

  return (
    <main className="page">
      <section className="container">
        <Header />

        <div className="summary-bar">
          <span>{t("totalNotes")}: {totalElements}</span>
          {editingId && <span>{t("editingMode")}</span>}
        </div>

        <FindNoteById />
        <NoteForm />

        <h2 className="section-title">{t("listSectionTitle")}</h2>
        <NoteList />
      </section>
    </main>
  );
}

export default App;
