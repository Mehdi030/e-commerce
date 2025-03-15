<template>
  <div class="flex items-center justify-center min-h-screen bg-white text-gray-900 dark:bg-black dark:text-white">
    <div class="max-w-md bg-gray-100 dark:bg-gray-800 p-6 rounded-lg shadow-lg text-center">
      <h2 class="text-3xl font-bold text-red-600">📝 Registrierung</h2>

      <p v-if="message" class="mt-3 p-3 bg-green-500 text-white rounded">{{ message }}</p>

      <form @submit.prevent="register" class="mt-4">
        <div class="text-left">
          <label class="block font-medium">👤 Benutzername:</label>
          <input type="text" v-model="username" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">
        </div>

        <div class="text-left mt-3">
          <label class="block font-medium">📧 Email:</label>
          <input type="email" v-model="email" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">
        </div>

        <div class="text-left mt-3">
          <label class="block font-medium">🔑 Passwort:</label>
          <input type="password" v-model="password" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">
        </div>

        <button type="submit" class="mt-4 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700 w-full">
          ✅ Registrieren
        </button>
      </form>
    </div>
  </div>
</template>
