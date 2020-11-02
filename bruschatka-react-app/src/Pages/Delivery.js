import React, {Component} from "react";
// import 'bootstrap/dist/css/bootstrap.css'
import axios from "axios"
import {Modal, ModalHeader, ModalBody} from "reactstrap"
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Navigation from "../Navigation";
import Clients from "./Clients";
import Products from "./Products";
import Archive from "./Archive";

class Delivery extends Component{
    constructor(props) {
        super(props);
        this.state = {
            delivery:[],
            date:""
        }
    }


    getDeliveries=(event)=> {
        const {name, value, type, checked} = event.target;
        type === "checkbox" ? this.setState({[name]: checked}) : this.setState({[name]: value});
        axios({
            url:"http://localhost:8080/deliveredProducts",
            method:"get",
            params: {
                date:event.target.value
            }
        }).then(res=> {
            this.setState({
                delivery: res.data
            })
        });
    };


    componentDidMount() {

    }

    render() {
        const {delivery, date} = this.state;
        return (
            <div>
                {/*<BrowserRouter>*/}
                {/*    <div>*/}
                {/*        <Navigation/>*/}
                {/*        <Switch>*/}
                {/*            <Route path="/clients" component={Clients} exact/>*/}
                {/*            <Route path="/products" component={Products}/>*/}
                {/*            <Route path="/delivery" component={Delivery}/>*/}
                {/*            <Route path="/archive" component={Archive}/>*/}
                {/*            /!*<Route component={Error}/>*!/*/}
                {/*        </Switch>*/}
                {/*    </div>*/}
                {/*</BrowserRouter>*/}
                <div className={"row"}>
                    <div className={"col"}>
                        {/*<input type={"text"} placeholder={"Qidirish..."} name={"filterName"} onChange={event => filterChange(event,1)}/>*/}

                        <h2 style={{color:"blue"}}>Dostavka</h2>
                        <h6>Sanani kiriting:</h6>
                        <input type={"date"} name={"date"} value={date} onChange={this.getDeliveries}/>

                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Klient</th>
                                <th>Mahsulot</th>
                                <th>Miqdori (m2)</th>
                                <th>Telefon</th>
                                <th>Adres</th>
                                <th>Olingan sana</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                delivery.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.clientName}</th>
                                        <th>{item.productName}</th>
                                        <th>{item.amount}</th>
                                        <th>{item.clientPhone}</th>
                                        <th>{item.address}</th>
                                        <th>{item.date}</th>
                                    </tr>
                                )

                            }
                            </tbody>
                        </table>

                        {/*<Pagination postsPerPage={postsPerPage} totalPosts={totalElements}*/}
                        {/*            paginate={page} pageNumber={currentPage}/>*/}

                    </div>
                </div>
            </div>
        )
    }
}

export default Delivery