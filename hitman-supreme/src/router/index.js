// Composables
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth.store';
import {pinia} from "@/plugins/pinia";
import NProgress from 'nprogress'

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/Home.vue'),
        meta: {
          public: true
        }
      },
      {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/About.vue'),
        meta: {
          public: true
        }
      },
      {
        path: '/register',
        name: 'Registration',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/RegistrationView.vue'),
        meta: {
          public: true
        }
      },
      {
        path: '/login',
        name: 'Login',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/Login'),
        meta: {
          public: true
        }
      },
      {
        path: '/missions',
        name: 'Missions',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/Missions'),
        meta:{
          requiresAuth: true,
          role: "USER"
        }
      },
      {
        path: '/target',
        name: 'Target',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/TargetView'),
        meta:{
          requiresAuth: true,
          role: "USER"
        }
      },
      {
        path: '/missionhub',
        name: 'MissionHub',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/MissionHubView'),
        meta:{
          requiresAuth: true,
          role: "ADMIN"
        }
      },
      {
        path: '/userhub',
        name: 'UserHub',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/UserHubView'),
        meta:{
          requiresAuth: true,
          role: "ADMIN"
        }
      },
      {
        path: '/admin',
        name: 'Admin',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/AdminView'),
        meta:{
          requiresAuth: true,
          role: "ADMIN"
        }
      },
      {
        path: '/user',
        name: 'User',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/UserView'),
        meta:{
          requiresAuth: true,
          role: "USER"
        }
      },
      {
        path: '/404',
        name: '404',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "home" */ '@/views/NotFound'),
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach(async (to) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const authenticate = useAuthStore();
  if (to.meta.requiresAuth){
    await authenticate.verifyAccount()
    if(!authenticate.user){
      return { name: 'Login' }
    }
    if (authenticate.user.role !== to.meta.role){
      if (authenticate.user.role === 'ADMIN'){
        return { name: 'Admin' }
      }
      else {
        return { name: 'User' }
      }
    }
  }
  if(to.meta.public && authenticate.user){
    if (authenticate.user.role === 'ADMIN'){
      return { name: 'Admin' }
    }
    else {
      return { name: 'User' }
    }
  }
})



export default router
