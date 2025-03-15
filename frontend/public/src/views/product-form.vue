<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Produkt hinzufügen</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-white text-gray-900 dark:bg-black dark:text-white flex items-center justify-center min-h-screen">
<div class="max-w-md bg-gray-100 dark:bg-gray-800 p-6 rounded-lg shadow-lg text-center">
    <h1 class="text-3xl font-bold text-red-600">➕ Neues Produkt</h1>

    <form th:action="@{/products/save}" method="post" class="mt-6 text-left">
        <label class="block font-medium">📦 Name:</label>
        <input type="text" name="name" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">

        <label class="block font-medium mt-4">💰 Preis:</label>
        <input type="number" name="price" step="0.01" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">

        <label class="block font-medium mt-4">📝 Beschreibung:</label>
        <textarea name="description" class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500"></textarea>

        <button type="submit" class="mt-6 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700 w-full">✅ Produkt speichern</button>
    </form>

    <a href="/public/src/views/product-list" class="mt-4 inline-block text-red-600 hover:underline">🔙 Zurück zur Produktliste</a>
</div>
</body>
</html>