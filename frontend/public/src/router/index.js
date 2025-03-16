import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
    history: createWebHashHistory(),  // Nutze Hash-Modus statt `createWebHistory()`
    routes: [
        { path: '/', component: Home },
        { path: '/cart', component: Cart }
    ]
});

export default router;
