<template>
  <div class="bg-white text-gray-900 dark:bg-black dark:text-white flex flex-col items-center min-h-screen">
    <!-- 🔥 Header mit Warenkorb-Button -->
    <header class="w-full p-4 flex justify-between items-center bg-gray-100 dark:bg-gray-800 shadow-md">
      <h1 class="text-3xl font-bold text-red-600">📦 Produktliste</h1>
      <button @click="toggleCart" class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700">
        🛒 Warenkorb
      </button>
    </header>

    <!-- 🛍 Produktliste -->
    <div class="grid grid-cols-2 gap-6 mt-6 max-w-4xl">
      <div v-for="product in products" :key="product.id" class="border border-gray-300 dark:border-gray-700 p-4 rounded-lg">
        <img :src="product.imageUrl" alt="Produktbild" class="w-full h-40 object-cover rounded">
        <h3 class="text-lg font-bold mt-2">{{ product.name }}</h3>
        <p class="text-gray-700 dark:text-gray-300 mt-2">{{ product.price }}€</p>

        <router-link :to="`/products/${product.id}`" class="text-red-600 hover:underline mt-2">
          🔍 Details
        </router-link>

        <button @click="addToCart(product)" class="mt-4 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700 w-full">
          🛒 In den Warenkorb
        </button>
      </div>
    </div>

    <!-- 🛒 SLIDE-IN WARENKORB -->
    <div v-if="cartOpen" @click="toggleCart" class="fixed inset-0 bg-black bg-opacity-50 z-50"></div>

    <div :class="{'translate-x-full': !cartOpen}" class="fixed right-0 top-0 w-80 h-full bg-white dark:bg-gray-800 shadow-lg transition-transform z-50">
      <div class="p-4 flex justify-between items-center bg-gray-200 dark:bg-gray-700">
        <h2 class="text-xl font-bold">🛒 Dein Warenkorb</h2>
        <button @click="toggleCart" class="text-gray-700 dark:text-gray-300 text-lg">✖</button>
      </div>

      <div class="p-4">
        <ul>
          <li v-for="cartItem in cart" :key="cartItem.id" class="flex justify-between py-2 border-b border-gray-300 dark:border-gray-700">
            <span>{{ cartItem.name }}</span>
            <span>{{ cartItem.quantity }}x</span>
            <span>{{ cartItem.price }}€</span>
            <button @click="removeFromCart(cartItem)" class="text-red-500 hover:underline">🗑️</button>
          </li>
        </ul>
      </div>

      <div class="p-4 border-t border-gray-300 dark:border-gray-700">
        <p class="text-lg font-bold">Gesamt: {{ totalPrice }}€</p>
        <button class="w-full bg-green-500 text-white px-4 py-2 rounded mt-4 hover:bg-green-700">
          ✅ Zur Kasse
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue';

export default {
  setup() {
    // 🔹 Produkte aus dem Backend laden
    const products = ref([]);
    const cart = ref([]);
    const cartOpen = ref(false);

    // 🛒 Fetch Products von Backend API
    onMounted(async () => {
      const response = await fetch('https://e-commerce-backend-uocd.onrender.com/api/products');
      products.value = await response.json();
    });

    // 🛍 Produkt zum Warenkorb hinzufügen
    const addToCart = (product) => {
      const existing = cart.value.find(item => item.id === product.id);
      if (existing) {
        existing.quantity += 1;
      } else {
        cart.value.push({ ...product, quantity: 1 });
      }
    };

    // 🗑️ Produkt aus Warenkorb entfernen
    const removeFromCart = (product) => {
      cart.value = cart.value.filter(item => item.id !== product.id);
    };

    // 🔄 Gesamtpreis berechnen
    const totalPrice = computed(() => {
      return cart.value.reduce((total, item) => total + item.price * item.quantity, 0);
    });

    // 🎛 Toggle Cart
    const toggleCart = () => {
      cartOpen.value = !cartOpen.value;
    };

    return { products, cart, cartOpen, addToCart, removeFromCart, totalPrice, toggleCart };
  }
};
</script>

<style scoped>
.translate-x-full {
  transform: translateX(100%);
}
</style>

