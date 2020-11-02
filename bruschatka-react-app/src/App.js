import React, {Component} from 'react';
import './App.css';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Clients from "./Pages/Clients"
import Navigation from "./Navigation";
import Products from "./Pages/Products";
import Delivery from "./Pages/Delivery";
import Archive from "./Pages/Archive";


class App extends Component{
    render() {
        return (
            <div>
                <BrowserRouter>
                    <div>
                        <Navigation/>
                        <Switch>
                            <Route path="/clients" component={Clients} />
                            <Route path="/products" component={Products} />
                            <Route path="/delivery" component={Delivery} />
                            <Route path="/archive" component={Archive} />
                            {/*<Route component={Error}/>*/}
                        </Switch>
                    </div>
                </BrowserRouter>
            </div>
        )
    }
}

export default App;
