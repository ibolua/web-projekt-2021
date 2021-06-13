<template>
  <div class="container">
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
        <FotoGalerieBild @entferne-foto="deleteFoto" :foto="ele" v-for="ele, in fotoitems" v-bind:key="ele.id"/>
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
    const suchfeld = ref("");
    const fotos: Ref<Foto[]> = ref([]);
    let index = 0;

    function geklickt() {
      if(index < fotoliste.length) {
        fotos.value.push(fotoliste[index]);
        index += 1;
      } else {
        alert("Keine Fotos mehr");
      }
    }

    const fotoitems = computed(() => {
      if (suchfeld.value.length < 3) {
        return fotos.value;
      } else {
        return fotos.value.filter((e: { ort: string; }) => 
          e.ort.toLowerCase().includes(suchfeld.value.toLowerCase())
        );
      }

    })


    function deleteFoto(id: number): void {
      fotos.value = fotos.value.filter(ele => ele.id !== id);

      // Irgendwie funktioniert das nicht so ganz.
      // Kann danach nicht nochmal 5 Bilder hinzufügen
      
      // weil hier ID gelöscht wird. Also eventuell auch mittendrin im Array.
      // Und oben wird einfach nacheinander hinzugefügt, ohne ID Berücksichtigung.
      index -= 1;
    }


    return {
      geklickt, suchfeld, fotoitems, deleteFoto
    }
  }


  
});
</script>


<style scoped>

</style>
