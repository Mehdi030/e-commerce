<template>
  <div class="flex items-center justify-center min-h-screen bg-white text-gray-900 dark:bg-black dark:text-white">
    <div class="bg-gray-100 dark:bg-gray-800 p-6 rounded-lg shadow-md w-96 text-center">
      <h2 class="text-3xl font-bold text-red-600">🔑 Login</h2>

      <div v-if="errorMessage" class="mt-3 p-3 bg-red-500 text-white rounded">
        ❌ {{ errorMessage }}
      </div>
      <div v-if="logoutMessage" class="mt-3 p-3 bg-green-500 text-white rounded">
        ✅ Erfolgreich abgemeldet!
      </div>

      <form @submit.prevent="login" class="mt-4">
        <div class="text-left">
          <label class="block font-medium">📧 Email:</label>
          <input type="email" v-model="email" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">
        </div>

        <div class="text-left mt-3">
          <label class="block font-medium">🔑 Passwort:</label>
          <input type="password" v-model="password" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">
        </div>

        <button type="submit" class="mt-4 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700 w-full">Login</button>
      </form>

      <p class="mt-4">Noch keinen Account?
        <router-link to="/register" class="text-red-600 hover:underline">Hier registrieren</router-link>
      </p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      email: "",
      password: "",
      errorMessage: null,
      logoutMessage: null,
    };
  },
  methods: {
    async login() {
      try {
        await axios.post(import.meta.env.VITE_API_URL + "/auth/login", {
          email: this.email,
          password: this.password,
        });
        this.$router.push("/");
      } catch (error) {
        this.errorMessage = "❌ Ungültige Anmeldeinformationen!";
      }
    },
  },
  mounted() {
    if (this.$route.query.logout) {
      this.logoutMessage = "✅ Erfolgreich abgemeldet!";
    }
  },
};
</script>
