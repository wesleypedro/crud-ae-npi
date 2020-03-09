import store from '../store'
import LoginPage from '../views/Login'
import Home from '../views/Home'
import Register from '../views/Register'

export default [


    /* Geral */
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/login',
        meta: {
            public: true,
        },
        props: true,
        name: 'Login',
        component: LoginPage
    },

    {
        path: '/register',
        meta: {
            public: true,
        },
        props: true,
        name: 'Register',
        component: Register
    },

    {
        path: '/logout',
        meta: { breadcrumb: true },
        name: 'Logout',
        beforeEnter (to, from, next) {
            store.dispatch('auth/logout', false)
        }
    },

];
