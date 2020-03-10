import store from '../store'
import LoginPage from '../views/Login'
import Home from '../views/Home'
import Register from '../views/Register'
import Cursos from '../views/Cursos'

export default [


    /* Geral */
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/cursos',
        name: 'Cursos',
        component: Cursos
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
