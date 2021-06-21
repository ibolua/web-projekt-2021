import { reactive, readonly } from "vue";
import { Foto, FotoMessage } from "./Foto";
import { Client, Message } from "@stomp/stompjs";

const wsurl = "ws://localhost:9090/messagebroker";
const DEST = "/topic/foto";

const stompclient = new Client({ brokerURL: wsurl })
stompclient.onWebSocketError = (event) => {/* WebSocket-Fehler */ }
stompclient.onStompError = (frame) => { /* STOMP-Fehler */ }

stompclient.onConnect = (frame) => {
    console.log("Verbindung aufgebaut");
    // Callback erfolgreicher Verbindungsaufbau zu Broker

    stompclient.subscribe(DEST, (message) => {
        console.log("In Subscribe drin");
        // Callback: Nachricht auf DEST empfangen
        // empfangene Nutzdaten in message.body abrufbar,
        // ggf. mit JSON.parse(message.body) zu JS konvertieren

        console.log("message.body: " + message.body);
        const o: FotoMessage = JSON.parse(message.body);

        if (["fotoGespeichert", "fotoGeloescht"].includes(o.operation)) {
            useFotoStore().updateFotos();
        }

        // if (o.operation === "fotoGespeichert") {
        //     // useFotoStore().getFoto(o.id);
        //     useFotoStore().getFoto(o.id);
        // }
        // if (o.operation === "fotoGeloescht") {
        //     useFotoStore().deleteFoto(o.id);
        // }



    });
};
stompclient.onDisconnect = () => { /* Verbindung abgebaut */ console.log("Verbindung getrennt"); }

// Verbindung zum Broker aufbauen
stompclient.activate();

// Nachrichtenversand vom Client zum Server
try {
    stompclient.publish({
        destination: DEST, headers: {},
        // body: JSON.stringify(datenobjekt);
        // ... oder body: "irgendein String"
    });
} catch (fehler) {
    // Problem beim Senden
}

const fotostate = reactive({
    fotos: Array<Foto>(),
    errormessage: ""
})

export function useFotoStore() {
    async function updateFotos(): Promise<void> {
        try {
            const url = "/api/foto"
            const response = await fetch(url)
            const jsondata: Array<Foto> = await response.json()

            console.log("In updateFotos() - jsondata:");
            console.log(jsondata);
            fotostate.fotos.length = 0;
            for (const foto of jsondata) {
                fotostate.fotos.push(foto);
            }

        } catch (reason) {
            fotostate.errormessage = "Fehler: ${reason}";
        }
    }

    async function deleteFoto(id: number): Promise<void> {
        try {
            const url = "api/foto/" + id;
            await fetch(url, { method: 'DELETE' });
        } catch (reason) {
            fotostate.errormessage = "Fehler: ${reason}";
        }
    }

    async function getFoto(id: number): Promise < void> {
        try {
            const url = "api/foto/" + id;
            const response = await fetch(url, {method: 'GET'});
            const jsondata: Foto = await response.json();

            fotostate.fotos.push(jsondata);
        } catch(reason) {
            fotostate.errormessage = "Fehler: ${reason}";
        }
    }

    return {
        fotostate: readonly(fotostate),
        updateFotos,
        deleteFoto,
        getFoto
    }
}
