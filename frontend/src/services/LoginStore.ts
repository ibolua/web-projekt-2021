import { reactive, readonly } from "vue";

const loginstate = reactive({
    username: "",
    jwttoken: "",
    errormessage: "",
    isLoggedIn: false
})

// const userobj = { loginname: 'ibo', rolle: 'PHOTOGRAPH' };

export function useLoginStore() {
    async function doLogin(username: string, password: string): Promise<boolean> {
        console.info("doLogin() Start");
        const credentials = btoa('${username}:${password}');
        const userobj = { 'username': username, 'password': password};

        try {
            const url = "/api/login";
            // const response = await fetch(url, {
            //     method: 'POST',
            //     headers: {
            //         'Authorization': 'Bearer ' + credentials,
            //         'Content-Type': 'application/json',
            //     },
            //     // body: JSON.stringify(userobj),
            // });


            const response = await fetch(url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json'},
                body: JSON.stringify(userobj),
            });

            const token = await response.text();

            // Wenn Antwort "ok 200"
            if(response.ok) {
                // Im Erfolgsfall
                loginstate.username = username;
                loginstate.jwttoken = token;
                loginstate.isLoggedIn = true;
                loginstate.errormessage = "";
                console.log("doLogin() erfolgreich");
                return true;
            }
            console.warn("doLogin() NICHIT erfolgreich: %s", response.status);
            return false;



        } catch (reason) {
            // Im Fehlerfall
            console.log("doLogin() fehlerhaft");
            doLogout();
            loginstate.errormessage = reason;
            return false;
        }

    }

    function doLogout() {
        console.info("doLogout() ausgeführt");
        loginstate.username = "";
        loginstate.jwttoken = "";
        loginstate.errormessage = "";
        loginstate.isLoggedIn = false;
    }


    return {
        loginstate: readonly(loginstate),
        doLogin: readonly(doLogin),
        doLogout: readonly(doLogout)
    }

}
