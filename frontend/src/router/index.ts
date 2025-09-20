import EmpresaCreateView from '@/views/empresa/EmpresaCreateView.vue'
import EmpresaEditView from '@/views/empresa/EmpresaEditView.vue'
import EmpresaView from '@/views/empresa/EmpresaView.vue'
import FornecedorCreateVIew from '@/views/fornecedor/FornecedorCreateVIew.vue'
import FornecedorEditVIew from '@/views/fornecedor/FornecedorEditVIew.vue'
import FornecedorView from '@/views/fornecedor/FornecedorView.vue'
import NotFound from '@/views/NotFound.vue'
import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/empresas', component: EmpresaView, name: 'Empresa', },
    { path: '/empresas/create', component: EmpresaCreateView, name: 'EmpresaCreate', },
    { path: '/empresas/edit/:id', component: EmpresaEditView, name: 'EmpresaEdit', },

    { path: '/fornecedores', component: FornecedorView, name: 'Fornecedor' },
    { path: '/fornecedores/create', component: FornecedorCreateVIew, name: 'FornecedorCreate', },
    { path: '/fornecedores/edit/:id', component: FornecedorEditVIew, name: 'FornecedorEdit', },

    { path: '/', redirect: { name: 'Empresa' }},

    // Rota coringa para 404
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: NotFound
    }
  ],
})

export default router
