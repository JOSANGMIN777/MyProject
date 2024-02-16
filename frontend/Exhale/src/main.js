import '@/assets/scss/main.scss'
import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import App from './App.vue'
import router from './router/index.js'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"

// fontawesome import
import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
library.add(fas)

const app = createApp(App)
const pinia = createPinia();

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
const vuetify = createVuetify({
  components,
  directives,
  icons: {
    defaultSet: 'mdi',
  }
})

// createApp을 통해 생성한Application 인스턴스의 component API 활용
app.component('font-awesome-icon', FontAwesomeIcon)

app.use(createPinia())
app.use(pinia);
pinia.use(piniaPluginPersistedstate); //새로고침시 데이터 유지를 위한 설정
app.use(router)
app.use(vuetify)
app.mount('#app')
