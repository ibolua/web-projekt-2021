<template>

    <!-- von Codepen https://codepen.io/stevehalford/pen/YeYEOR?editors=1010 -->
    <section class="hero">
        <div class="hero-body">
            <div class="container">
                <div class="columns is-centered">
                    <!-- <div class="column is-5-tablet is-4-desktop is-3-widescreen"> -->
                    <div class="column is-half">
                    <form action="" class="box">
                        <h1 class="title is-centered">Login zur MI Foto-Community</h1>
                        <div class="field">
                        <div class="control has-icons-left">
                            <input type="input" name="username" placeholder="Username" class="input" required>
                            <span class="icon is-small is-left">
                            <em class="fa fa-user"></em>
                            </span>
                        </div>
                        </div>
                        <div class="field">
                        <div class="control has-icons-left">
                            <input type="password" name="password" placeholder="*******" class="input" required>
                            <span class="icon is-small is-left">
                            <em class="fa fa-lock"></em>
                            </span>
                        </div>
                        </div>

                        <div class="field">
                        <button class="button is-primary is-fullwidth">
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
import { defineComponent } from 'vue'
import { useLoginStore} from '@/services/LoginStore'

export default defineComponent({
    name: 'LoginView',

    setup() {
        const { loginstate, doLogin, doLogout } = useLoginStore();
        doLogout();


    },
})
</script>
