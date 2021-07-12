<template>
  <!-- von Codepen https://codepen.io/stevehalford/pen/YeYEOR?editors=1010 -->
  <section class="hero">
    <div class="hero-body">
      <div class="container">
        <div class="columns is-centered">
          <div class="column is-half">
            <form action="" class="box">
              <h1 class="title is-centered">Login zur MI Foto-Community</h1>
              <div class="field">
                <div class="control has-icons-left">
                  <input
                    v-model="username"
                    type="input"
                    name="username"
                    placeholder="Username"
                    class="input"
                    required
                  />
                  <span class="icon is-small is-left">
                    <em class="fa fa-user"></em>
                  </span>
                </div>
              </div>
              <div class="field">
                <div class="control has-icons-left">
                  <input
                    v-model="password"
                    type="password"
                    name="password"
                    placeholder="*******"
                    class="input"
                    required
                  />
                  <span class="icon is-small is-left">
                    <em class="fa fa-lock"></em>
                  </span>
                </div>
              </div>

              <div class="field">
                <button
                  @click.prevent="login(username, password)"
                  class="button is-primary is-fullwidth"
                >
                  Login
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </section>
  <!-- von Codepen -->
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { useLoginStore } from "@/services/LoginStore";
import { useRouter } from "vue-router";

export default defineComponent({
  name: "LoginView",

  setup() {
    const router = useRouter();
    const { doLogin, doLogout } = useLoginStore();
    doLogout();

    async function login(u: string, p: string) {
      console.log("login geklickt");
      if (await doLogin(u, p)) {
        router.replace("/");
      }
    }

    return {
      login,
    };
  },
});
</script>
