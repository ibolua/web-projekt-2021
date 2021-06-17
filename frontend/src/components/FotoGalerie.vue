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
        <FotoGalerieBild @entferne-foto="deleteFoto" :foto="ele" v-for="ele, in fotoitems" v-bind:key="ele.id"/>
      </div>
    </section>
    <p>Insgesamt {{anzahlFotos}} Bilder</p>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, reactive, ref, Ref, onMounted } from "vue";
import FotoGalerieBild from "./FotoGalerieBild.vue";
import { Foto } from "@/services/Foto";
import { fotoliste } from "@/services/FotoListe";
import { useFotoStore } from "@/services/FotoStore";

export default defineComponent({
  name: "FotoGalerie",
  
  components: {
    FotoGalerieBild
  },

  setup() {
    const suchfeld = ref("");
    const fotos: Ref<Foto[]> = ref([]);
    let index = 0;
    const { errormessage, fotostate, updateFotos } = useFotoStore();
    // const fotostate = useFotoStore();

    const anzahlFotos = computed(() => fotostate.length)

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
    
    onMounted(async () => {
      await updateFotos();
    })



    return {
      geklickt, suchfeld, fotoitems, deleteFoto, errormessage, fotostate, anzahlFotos
    }
  }


  
});
</script>


<style scoped>

</style>
