import React, {Component} from "react";
import 'bootstrap/dist/css/bootstrap.css'
import axios from "axios"
import {Modal, ModalHeader, ModalBody} from "reactstrap"
import Pagination from "../Pagination";
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Navigation from "../Navigation";
import Clients from "./Clients";
import Products from "./Products";
import Delivery from "./Delivery";

class Archive extends Component {
    constructor(props) {
        super(props);
        this.state = {
            clients: [],
            products: [],
            payments: [],
            currentPage: 1,
            totalPages: 0,
            totalElements: 0,
            postsPerPage: 5,
            modalAddVisible: false,
            productId: 0,
            phone: 0,
            modalEditVisible: false,
            modalAllPayments: false,

            editId: 0,
            name: "",
            productName: "",
            amount: 0,
            price: 0,
            phoneNumber: "",
            address: "",
            modalPaymentVisible: false,

            paymentId: 0,
            payTypeId: 0,
            clientName:"",
            overallAmount:0,
            filterName:"",
            totalDebt:0,
            clientOption:1
        }
    }

    getClients = () => {
        axios({
            url: "http://localhost:8080/getClientsArchive",
            method: "get",
            params: {
                page: 1,
                name: ""
            }
        }).then(res => {
            this.setState({
                clients: res.data.clients,
                currentPage: 1,
                totalPages: res.data.totalPages,
                totalElements: res.data.overallElements
            })
        })
    };

    getProducts = () => {
        axios({
            url: "http://localhost:8080/getStock",
            method: "get",
        }).then(res => {
            this.setState({
                products: res.data
            })
        })
    };

    componentDidMount() {
        this.getClients();
        this.getProducts();
    }

    render() {
        const {clients, modalAddVisible, totalDebt, filterName, currentPage, totalElements, postsPerPage, overallAmount, clientName, payments, modalAllPayments, modalPaymentVisible, payTypeId, paymentId, modalEditVisible, editId, name, productName, amount, price, phoneNumber, address, phone, productId, products} = this.state;

        const handleSubmitAdd = (event) => {
            event.preventDefault();
            axios({
                url: "http://localhost:8080/addClient",
                method: "post",
                data: {
                    name: event.target[0].value,
                    productName: event.target[1].value,
                    amount: event.target[2].value,
                    price: event.target[3].value,
                    phoneNumber: "+" + event.target[4].value,
                    address: event.target[5].value
                }
            }).then(res => {
                if (res.data.success) {
                    alert(res.data.message);
                    this.setState({
                        modalAddVisible: false
                    });
                    this.componentDidMount();
                } else {
                    alert(res.data.message);
                }
            })
        };

        const page = (page, name) => {

            axios({
                url: "http://localhost:8080/getClients",
                method: "get",
                params: {
                    page: page,
                    name: name
                }
            }).then(res => {
                this.setState({
                    clients: res.data.clients,
                    currentPage: page,
                    totalPages: res.data.totalPages,
                    totalElements: res.data.overallElements
                });
            })
        };

        const handleSubmitEdit = (event) => {
            event.preventDefault();
            axios({
                url: "http://localhost:8080/editClient",
                method: "put",
                data: {
                    name: event.target[0].value,
                    productName: event.target[1].value,
                    amount: event.target[2].value,
                    price: event.target[3].value,
                    phoneNumber: "+" + event.target[4].value,
                    address: event.target[5].value
                },
                params: {
                    id: editId
                }
            }).then(res => {
                if (res.data.success) {
                    alert(res.data.message);
                    this.setState({
                        modalEditVisible: false
                    });
                    this.componentDidMount();
                }
            })
        };

        const deleteClient = (id) => {
            axios({
                url: "http://localhost:8080/deleteClient",
                method: "delete",
                params: {
                    id: id
                }
            }).then(res => {
                alert(res.data.message);
                close();
                this.componentDidMount()
            })
        };

        const makePayment = (event) => {
            event.preventDefault();
            axios({
                url: "http://localhost:8080/makePayment",
                method: "post",
                data: {
                    amount: event.target[0].value,
                    payTypeId: event.target[1].value,
                    clientId: paymentId
                }
            }).then(res => {
                alert(res.data.message);
                this.setState({
                    payTypeId:1
                });
                close();
                this.componentDidMount();
            })
        };

        const getPayments = (id, name, phone) => {
            axios({
                url: "http://localhost:8080/getPaymentsArchive",
                method: "get",
                params: {
                    id: id,
                    phone: phone
                }
            }).then(res => {
                this.setState({
                    payments: res.data,
                    clientName:name,
                    modalAllPayments: true
                });
            });
            //
            axios({
                url: "http://localhost:8080/GetOverallAmountA",
                method: "get",
                params: {
                    id: id
                }
            }).then(res=>{
                this.setState({
                    overallAmount: res.data
                })
            });

            axios({
                url: "http://localhost:8080/GetTotalDebtA",
                method: "get",
                params: {
                    id: id
                }
            }).then(res=>{
                this.setState({
                    totalDebt: res.data
                })
            })
        };

        const filterChange = (event, page) => {
            this.setState({
                filterName: event.target.value
            });
            axios({
                url: "http://localhost:8080/getClientsArchive",
                method: "get",
                params: {
                    page: page,
                    name: event.target.value,
                    productName:event.target.value,
                    phoneNumber: event.target.value
                }
            }).then(res => {
                this.setState({
                    clients: res.data.clients,
                    currentPage: page,
                    totalPages: res.data.totalPages,
                    totalElements: res.data.overallElements
                });
            })
        };


        const openModal = () => {
            this.setState({
                modalAddVisible: true,
                phone: 998
            })
        };

        const openPaymentModal = (id) => {
            this.setState({
                modalPaymentVisible: true,
                paymentId: id
            })
        };

        const openModal1 = (id, name, productName, amount, price, phoneNumber, address) => {
            this.setState({
                modalEditVisible: true,
                editId: id,
                name: name,
                productName: productName,
                amount: amount,
                price: price,
                phoneNumber: phoneNumber,
                address: address
            })
        };

        const close = () => {
            this.setState({
                modalAddVisible: false,
                modalEditVisible: false,
                modalPaymentVisible: false,
                modalAllPayments: false
            })
        };

        const handleTypes = (event) => {
            const {name, value, type, checked} = event.target;
            type === "checkbox" ? this.setState({[name]: checked}) : this.setState({[name]: value});
        };


        return (
            <div>
                <h2 style={{color:"blue"}}>Arxiv</h2>

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

                <Modal isOpen={modalAllPayments}>
                    <ModalHeader>
                        To'lovlar
                    </ModalHeader>
                    <ModalBody>
                        <h6>Klient: {clientName}</h6>
                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Summa</th>
                                <th>Sana</th>
                                <th>To'lov turi</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                payments.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.amount + " "}</th>
                                        <th>{item.paymentDate}</th>
                                        <th>{item.payTypeName}</th>
                                    </tr>
                                )
                            }
                            </tbody>
                        </table>
                        <h6>Umumiy: {overallAmount}/{totalDebt}</h6>
                        <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                    </ModalBody>
                </Modal>


                <div className={"row"}>
                    <div className={"col"}>

                        <input type={"text"} placeholder={"Qidirish..."} name={"filterName"} onChange={event => filterChange(event,1)}/>



                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Ism</th>
                                <th>Mahsulot</th>
                                <th>Qiymat(m2)</th>
                                <th>Narx(so'm)</th>
                                <th>Telefon raqam</th>
                                <th>Manzil</th>
                                <th>Sana</th>
                                <th>To'lov</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                clients.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.name}</th>
                                        <th>{item.productName}</th>
                                        <th>{item.amount}</th>
                                        <th>{item.price}</th>
                                        <th>{item.phoneNumber}</th>
                                        <th>{item.address}</th>
                                        <th>{item.orderDate}</th>
                                        <th>
                                            <button type={"button"} className={"btn btn-outline-success float-sm-right"}
                                                    onClick={() => getPayments(item.id, item.name, item.phoneNumber)}>To'lovlar
                                            </button>
                                        </th>
                                    </tr>
                                )

                            }
                            </tbody>
                        </table>

                        <Pagination postsPerPage={postsPerPage} totalPosts={totalElements}
                                    paginate={page} pageNumber={currentPage} name={filterName}/>

                    </div>
                </div>
            </div>
        )
    }
}

export default Archive