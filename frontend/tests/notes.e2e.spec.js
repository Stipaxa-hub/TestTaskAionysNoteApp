import { test, expect } from "@playwright/test";

test.describe("Notes App", () => {
  test("full CRUD flow and language switch", async ({ page }) => {
    await page.goto("/");

    // --- Verify initial shell ---
    await expect(page.getByRole("heading", { name: "Notes", exact: true })).toBeVisible();
    await expect(page.getByLabel("Title")).toBeVisible();
    await expect(page.getByLabel("Content")).toBeVisible();

    // --- Language switch ---
    await page.getByLabel("language").selectOption("uk");
    await expect(page.getByRole("heading", { name: "Нотатки", exact: true })).toBeVisible();
    // Switch back to English for CRUD tests
    await page.getByLabel("language").selectOption("en");
    await expect(page.getByRole("heading", { name: "Notes", exact: true })).toBeVisible();

    // --- Create a note ---
    const uniqueTitle = `E2E Note ${Date.now()}`;
    const noteContent = `E2E content ${Date.now()}`;

    await page.getByLabel("Title").fill(uniqueTitle);
    await page.getByLabel("Content").fill(noteContent);
    await page.getByRole("button", { name: "Create" }).click();

    // Wait for the new note to appear (store navigates to last page after create)
    const createdItem = page.locator("li", { hasText: uniqueTitle });
    await expect(createdItem).toBeVisible({ timeout: 15000 });
    await expect(createdItem.getByText(noteContent)).toBeVisible();

    // --- Edit the note ---
    const noteItem = page.locator("li", { hasText: uniqueTitle });
    await noteItem.getByRole("button", { name: "Edit" }).click();

    const updatedTitle = `${uniqueTitle} (edited)`;
    await page.getByLabel("Title").fill(updatedTitle);
    await page.getByRole("button", { name: "Update" }).click();

    // Verify updated title appears
    await expect(page.getByText(updatedTitle)).toBeVisible({ timeout: 15000 });

    // --- Delete the note ---
    const updatedNoteItem = page.locator("li", { hasText: updatedTitle });
    await updatedNoteItem.getByRole("button", { name: "Delete" }).click();

    // Verify the note is removed
    await expect(page.getByText(updatedTitle)).not.toBeVisible({ timeout: 15000 });
  });
});
