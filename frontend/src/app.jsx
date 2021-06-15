const myElement = React.createElement(
    'p',
    {},
    'Das ist mein erstes React Element'
);

const myJSXElement = <p id="my-id">Das ist meine erste JSX Komponente</p>


ReactDOM.render(
    myJSXElement,
    document.getElementById("react-app")
);
