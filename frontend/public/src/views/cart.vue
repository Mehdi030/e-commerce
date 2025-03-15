<template>
    <div class="max-w-4xl bg-gray-100 dark:bg-gray-800 p-6 rounded-lg shadow-lg">
        <h1 class="text-3xl font-bold text-red-600">🛒 Dein Warenkorb</h1>

        <table class="w-full mt-6 border border-gray-300 dark:border-gray-700">
            <thead class="bg-gray-100 dark:bg-gray-800">
            <tr>
                <th class="p-3">Produkt</th>
                <th class="p-3">Menge</th>
                <th class="p-3">Preis</th>
                <th class="p-3">Aktion</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in cartItems" :key="item.product.id" class="border-t border-gray-300 dark:border-gray-700">
                <td class="p-3">{{ item.product.name }}</td>
                <td class="p-3">
                    <input type="number" v-model="item.quantity" min="1" class="p-2 border w-16" />
                    <button @click="updateCart(item.product.id, item.quantity)" class="ml-2 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700">🔄 Aktualisieren</button>
                </td>
                <td class="p-3">{{ item.price }} €</td>
                <td class="p-3">
                    <button @click="removeFromCart(item.product.id)" class="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700">🗑️ Entfernen</button>
                </td>
            </tr>
            </tbody>
        </table>

        <p class="mt-4 text-xl font-bold">Gesamtpreis: {{ totalPrice }} €</p>

        <button @click="clearCart" class="mt-4 bg-red-500 text-white px-4 py-2 rounded hover:bg-red-700">🗑️ Warenkorb leeren</button>

        <router-link to="/products" class="mt-4 inline-block text-red-600 hover:underline">🔙 Zurück zu den Produkten</router-link>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        data() {
            return {
                cartItems: [],
                totalPrice: 0,
            };
        },
        methods: {
            async fetchCart() {
                try {
                    const response = await axios.get(import.meta.env.VITE_API_URL + "/cart");
                    this.cartItems = response.data.cartItems;
                    this.totalPrice = response.data.totalPrice;
                } catch (error) {
                    console.error("Fehler beim Laden des Warenkorbs:", error);
                }
            },
            async updateCart(productId, quantity) {
                try {
                    await axios.post(import.meta.env.VITE_API_URL + `/cart/update/${productId}`, { quantity });
                    this.fetchCart(); // Warenkorb neu laden
                } catch (error) {
                    console.error("Fehler beim Aktualisieren des Warenkorbs:", error);
                }
            },
            async removeFromCart(productId) {
                try {
                    await axios.post(import.meta.env.VITE_API_URL + `/cart/remove/${productId}`);
                    this.fetchCart();
                } catch (error) {
                    console.error("Fehler beim Entfernen des Produkts:", error);
                }
            },
            async clearCart() {
                try {
                    await axios.post(import.meta.env.VITE_API_URL + "/cart/clear");
                    this.cartItems = [];
                    this.totalPrice = 0;
                } catch (error) {
                    console.error("Fehler beim Leeren des Warenkorbs:", error);
                }
            },
        },
        mounted() {
            this.fetchCart();
        },
    };
</script>

<style scoped>
    /* Falls du noch extra Styles brauchst */
</style>
