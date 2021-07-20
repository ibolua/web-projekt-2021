<template>
  <section id="app">
    <div class="hero is-primary">
      <div class="hero-body">
        <h1 class="title">MI Foto Community</h1>
        <!-- <div v-if="user"> -->
          <p class="subtitle">{{user}} ist eingeloggt</p>
        <!-- </div> -->
      </div>
    </div>

    <div class="block">
      <nav class="navbar">
        <div class="navbar-menu is-active">
          <router-link to="/" class="navbar-item">Übersicht</router-link>
          <router-link to="/about" class="navbar-item">About</router-link>
        </div>
        <div class="navbar-end">
          <div class="navbar-item">
            <router-link to="/login" class="navbar-item">Login/Logout</router-link>
          </div>
        </div>
      </nav>
    </div>

    <div class="block">
      <div class="container">
          <router-view />
      </div>
    </div>
  </section>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";
import { useLoginStore} from '@/services/LoginStore'


export default defineComponent({
  name: "App",
  components: {},

  setup() {
    const { loginstate } = useLoginStore();
    const user = ref(loginstate.username);
    console.log("user: {}", user);

    return {
      user: loginstate.username
    };
  }
});
</script>
