import { useTranslation } from "react-i18next";
import { useNotesStore } from "../store/useNotesStore";

function Pagination() {
  const { t } = useTranslation();
  const { currentPage, totalPages, totalElements, pageSize, setPage, setPageSize } =
    useNotesStore();

  if (totalPages <= 0) return null;

  const pages = [];
  for (let i = 0; i < totalPages; i++) {
    pages.push(i);
  }

  return (
    <nav className="pagination" aria-label={t("paginationLabel")}>
      <div className="pagination-row">
        <button
          className="btn btn-muted"
          type="button"
          disabled={currentPage === 0}
          onClick={() => setPage(currentPage - 1)}
        >
          {t("prevPage")}
        </button>

        <div className="page-numbers">
          {pages.map((p) => (
            <button
              key={p}
              className={`btn ${p === currentPage ? "btn-primary" : "btn-muted"}`}
              type="button"
              onClick={() => setPage(p)}
            >
              {p + 1}
            </button>
          ))}
        </div>

        <button
          className="btn btn-muted"
          type="button"
          disabled={currentPage >= totalPages - 1}
          onClick={() => setPage(currentPage + 1)}
        >
          {t("nextPage")}
        </button>
      </div>

      <div className="pagination-info">
        <span>{t("pageOf", { current: currentPage + 1, total: totalPages })}</span>
        <span className="page-sep">·</span>
        <span>{t("totalNotes")}: {totalElements}</span>
        <span className="page-sep">·</span>
        <label className="page-size-label">
          {t("pageSizeLabel")}
          <select
            value={pageSize}
            onChange={(e) => setPageSize(Number(e.target.value))}
          >
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
          </select>
        </label>
      </div>
    </nav>
  );
}

export default Pagination;
