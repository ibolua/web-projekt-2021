<template>
  <div class="card column is-3 has-background-grey-lighter m-3">
    <div class="card-header">
      <p class="card-header-title is-centered">
        <!-- Dateinamen ausgeben -->
        {{ rFoto.dateiname }}
      </p>
      <!-- Lösch-Button -->
      <button
        @click="delclicked()"
        class="button card-header-icon has-background-grey-light"
      >
        <i class="fa fa-times" />
      </button>
    </div>
    <div class="card-content has-text-centered">
      <!-- Bild anzeigen -->

      <!-- So wollte ich es machen, aber funzt nicht. -->
      <router-link to="/detail/{{rFoto.id}}">TEST {{rFoto.id}}</router-link>

      <!-- so gehts anscheinend, aber ich wollte es so in der Art wie oben lösen eigentlich -->
      <router-link :to="{ name: 'FotoDetailView', params: {fotoid: rFoto.id } }" >
        <figure class="image is-inline-block">
          <img :src="url" />
        </figure>
      </router-link>

      <div class="content">
        <foto-star-rating :maxsterne="5" />
      </div>
      <!-- Ort -->
      {{ rFoto.ort }}
      <div class="content">Irgendwo</div>
      <!-- Zeitstempel -->
      {{ rFoto.zeitstempel }}
      <div class="has-text-grey">Irgendwann</div>
    </div>
  </div>
</template>


<script lang="ts">
import { defineComponent, PropType, reactive, ref } from "vue";
import FotoStarRating from "./FotoStarRating.vue";

export default defineComponent({
  components: { FotoStarRating },
  name: "FotoGalerieBild",

  props: {
    foto: { type: Object, required: true },
  },

  setup(props, context) {
    const rFoto = reactive(props.foto);

    function delclicked(): void {
      context.emit("entferne-foto", props.foto.id);
    }

    return {
      url: "/api/foto/" + rFoto.id,
      rFoto,
      delclicked,
    };
  },
});
</script>
