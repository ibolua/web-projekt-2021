<template>
  <div class="container">
    <div class="notification is-danger" v-if="errormessage"></div>
    <!-- Button zum Hinzufügen des nächsten Bildes -->
    <button @click="geklickt()" class="button">
      <i class="fas fa-camera" />
    </button>
    <!-- Eingabefeld für inkrementelle Suche -->
    <section class="section">
      <input type="text" v-model="suchfeld" class="input" placeholder="Suche" />
    </section>
    <section class="section">
      <div class="columns is-multiline">
        <!-- Hier alle Bilder mit Hilfe der FotoGalerieBild-Komponente anzeigen -->
        <!-- flexibel natürlich - nicht die fünf Beispielbilder hardcoden! -->
        <FotoGalerieBild
          @entferne-foto="deleteFoto"
          :foto="ele"
          v-for="ele in fotoitems"
          v-bind:key="ele.id"
        />
      </div>
    </section>
    <p>Insgesamt {{ anzahlFotos }} Bilder</p>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, reactive, ref, Ref, onMounted } from "vue";
import FotoGalerieBild from "./FotoGalerieBild.vue";
import { useFotoStore } from "@/services/FotoStore";

export default defineComponent({
  name: "FotoGalerie",

  components: {
    FotoGalerieBild,
  },

  setup() {
    const suchfeld = ref("");
    let index = 0;
    const { fotostate, updateFotos, deleteFoto } = useFotoStore();
    const errormessage = fotostate.errormessage;
    const fotos = ref(fotostate.fotos);

    const anzahlFotos = computed(() => fotos.value.length);

    function geklickt() {
      console.log("Fotoapparat geklickt");
    }

    const fotoitems = computed(() => {
      if (suchfeld.value.length < 3) {
        return fotos.value;
      } else {
        return fotos.value.filter((e: { ort: string }) =>
          e.ort.toLowerCase().includes(suchfeld.value.toLowerCase())
        );
      }
    });

    onMounted(async () => {
      await updateFotos();
    });

    return {
      geklickt,
      suchfeld,
      fotoitems,
      deleteFoto,
      errormessage,
      anzahlFotos,
    };
  },
});
</script>


<style scoped>
</style>
