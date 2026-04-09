import { useTranslation } from "react-i18next";

function Header() {
  const { t, i18n } = useTranslation();

  return (
    <header className="topbar">
      <div>
        <h1>{t("title")}</h1>
        <p className="subtitle">{t("subtitle")}</p>
      </div>
      <label className="lang-switcher">
        {t("language")}{" "}
        <select
          aria-label="language"
          value={i18n.language}
          onChange={(e) => i18n.changeLanguage(e.target.value)}
        >
          <option value="en">EN</option>
          <option value="uk">UK</option>
        </select>
      </label>
    </header>
  );
}

export default Header;
