<template>
  <div class="flex items-center justify-center min-h-screen bg-white text-gray-900 dark:bg-black dark:text-white">
    <div class="max-w-md bg-gray-100 dark:bg-gray-800 p-6 rounded-lg shadow-lg text-center">
      <h1 class="text-3xl font-bold text-red-600">➕ Neues Produkt</h1>

      <form @submit.prevent="addProduct" class="mt-6 text-left">
        <label class="block font-medium">📦 Name:</label>
        <input type="text" v-model="name" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">

        <label class="block font-medium mt-4">💰 Preis:</label>
        <input type="number" v-model="price" step="0.01" required class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500">

        <label class="block font-medium mt-4">📝 Beschreibung:</label>
        <textarea v-model="description" class="w-full p-2 border rounded focus:ring-2 focus:ring-red-500"></textarea>

        <button type="submit" class="mt-6 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700 w-full">
          ✅ Produkt speichern
        </button>
      </form>

      <router-link to="/products" class="mt-4 inline-block text-red-600 hover:underline">
        🔙 Zurück zur Produktliste
      </router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      name: "",
      price: "",
      description: "",
    };
  },
  methods: {
    async addProduct() {
      await axios.post(import.meta.env.VITE_API_URL + "/products/save", {
        name: this.name,
        price: this.price,
        description: this.description,
      });
      alert("✅ Produkt wurde erfolgreich gespeichert!");
      this.$router.push("/products");
    },
  },
};
</script>
