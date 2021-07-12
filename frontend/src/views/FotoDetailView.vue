<template>
  <h1 class="title">Detailansicht</h1>
  <p class="card-header-title is-centered">
    <!-- Dateinamen ausgeben -->
    {{ rFoto.dateiname }}
  </p>
  <p class="card-header-title is-centered">
    <!-- Zeitstempel -->
    {{ rFoto.zeitstempel }}
  </p>
  <div class="card-content has-text-centered">
    <!-- Bild anzeigen -->
    <figure class="image is-inline-block">
      <img :src="url" />
    </figure>
    <div class="content">
      <foto-star-rating :maxsterne="5" />
    </div>
    <!-- Ort -->
    {{ rFoto.ort }}
  </div>

  <router-link to="/" class="buttons has-addons is-centered">
    <button class="button is-primary">Zurück</button>
  </router-link>
</template>


<script lang="ts">
import { useFotoStore } from "@/services/FotoStore";
import { defineComponent, reactive } from "vue";

export default defineComponent({
  name: "FotoDetailView",

  props: {
    fotoid: { type: String, required: true },
  },

  setup(props) {
    const { fotostate } = useFotoStore();
    var id: number = parseInt(props.fotoid);
    let urlString = "/api/foto/";

    console.log("FOTOID STRING: " + props.fotoid);
    console.log("FOTOID INT: " + id);
    const rFoto = fotostate.fotos.find((f) => f.id === id) ?? null;
    if (rFoto !== null) {
      urlString += rFoto.id;
    } else {
      urlString += id;
    }

    return {
      url: urlString,
      rFoto,
    };
  },
});
</script>
