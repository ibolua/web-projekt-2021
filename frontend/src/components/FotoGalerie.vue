<template>
  <div class="container">
    <!-- Button zum Hinzufügen des nächsten Bildes -->
    <button @click="geklickt()" class="button">
      <i class="fas fa-camera" />
    </button>
    <!-- Eingabefeld für inkrementelle Suche -->
    <section class="section">
      <input type="text" class="input" placeholder="Suche" />
    </section>
    <section class="section">
      <div class="columns is-multiline">
        <!-- Hier alle Bilder mit Hilfe der FotoGalerieBild-Komponente anzeigen -->
        <!-- flexibel natürlich - nicht die fünf Beispielbilder hardcoden! -->
        
        <FotoGalerieBild v-for="i in fotoslen" v-bind:key="i" />
   

        

      </div>
    </section>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, reactive, ref, Ref } from "vue";
import FotoGalerieBild from "./FotoGalerieBild.vue";
import { Foto } from "@/services/Foto";
import { fotoliste } from "@/services/FotoListe";

export default defineComponent({
  name: "FotoGalerie",
  
  components: {
    FotoGalerieBild
  },

  setup() {
    var fotos: Foto[] = reactive([]);
    // fotosArray = fotoliste;
    // const fotos = reactive(fotosArray);
    // const fotoslen = computed( () => fotos.length);


    // fotos = fotoliste;

    // const fotoslen = computed( () => 7);
    const fotoslen = computed( () => fotos.length);
    let index = 0;

    function geklickt() {
      if(index < fotoliste.length) {
        fotos.push(fotoliste[index]);
        index += 1;
        console.log("ich war hier");
      } else {
        alert("Keine Fotos mehr");
      }
    }

    return {
      fotos, fotoslen, geklickt, index
    }
  }


  
});
</script>


<style scoped>

</style>
