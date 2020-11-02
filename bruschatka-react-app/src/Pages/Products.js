import React, {Component} from "react";
// import 'bootstrap/dist/css/bootstrap.css'
import axios from "axios"
import {Modal, ModalHeader, ModalBody} from "reactstrap"
import Pagination from "../Pagination";
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Navigation from "../Navigation";
import Clients from "./Clients";
import Delivery from "./Delivery";
import Archive from "./Archive";

class Products extends Component{
    constructor(props) {
        super(props);
        this.state = {
            products:[],
            stock:[],
            modalAddVisible:false,
            productName:"",
            yesterdayProd:[],
            modalYesterdayVisible: false,
            totalAmount:0,
            date:0,
            modalStockVisible:false
        }
    }

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
        this.getProducts();
    }

    render() {
        const {products, stock, date, modalStockVisible, totalAmount, modalYesterdayVisible, yesterdayProd, modalAddVisible, productName} = this.state;

        const openModal=()=> {
            this.setState({
                modalAddVisible: true
            })
        };

        const openStockModal=()=> {
            this.setState({
                modalStockVisible: true
            })
        }

        const addProduct=(event)=> {
            event.preventDefault();
            axios({
                url:"http://localhost:8080/addProduct",
                method:"post",
                data: {
                    name:event.target[0].value,
                    amount:event.target[1].value
                }
            }).then(res=>{
                alert(res.data.message);
                close();
                this.componentDidMount()
            })
        };

        const producedProducts=(event)=> {

            const {name, value, type, checked} = event.target;
            type === "checkbox" ? this.setState({[name]: checked}) : this.setState({[name]: value});

            axios({
                url:"http://localhost:8080/totalProducts",
                method:"get",
                params: {
                    date: event.target.value
                }
            }).then(res=> {
                this.setState({
                    totalAmount:res.data
                })
            });

            axios({
                url:"http://localhost:8080/producedProducts",
                method:"get",
                params: {
                    date:event.target.value
                }
            }).then(res=> {
                this.setState({
                    yesterdayProd: res.data
                })
            });
        };

        const openProducts=()=>{
            this.setState({
                modalYesterdayVisible:true
            })
        };


        const close=()=> {
            this.setState({
                modalAddVisible: false,
                modalYesterdayVisible: false,
                modalTodayVisible:false,
                modalStockVisible:false,
                totalAmount:0,
                yesterdayProd:[],
                date:null
            });
        };

        const addStock=(event)=> {
            axios({
                url:"http://localhost:8080/addStock",
                method:"post",
                data: {
                    name: event.target[0].value,
                    amount: 0
                }
            }).then(res => {
                alert(res.data.message);
                close();
                this.componentDidMount();
            })
        };

        const deleteStock=(id)=> {
            axios({
                url:"http://localhost:8080/deleteStock",
                method:"delete",
                params: {
                    id: id
                }
            }).then(res=> {
                alert(res.data.message);
                this.componentDidMount();
            })
        };

        const handleTypes = (event) => {
            const {name, value, type, checked} = event.target;
            type === "checkbox" ? this.setState({[name]: checked}) : this.setState({[name]: value});
        };


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
                <Modal isOpen={modalAddVisible}>
                    <ModalHeader>
                        Mahsulot qo'shish
                    </ModalHeader>
                    <ModalBody>
                        <form onSubmit={addProduct}>
                            <label>Mahsulot: </label>
                            <select className={"form-control"} name={"productName"} value={productName}
                                    onChange={handleTypes} required>
                                <option value={""}>Tanlang...</option>
                                {
                                    products.map(item =>
                                        <option value={item.name}>{item.name}</option>
                                    )
                                }
                            </select> <br/>

                            <label>Miqdori: </label>
                            <input className={"form-control"} type={"number"} placeholder={"Miqdori"} required/><br/>


                            <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                            <button style={{marginLeft: 10}} className={"btn btn-success"}>Qo'shish</button>
                        </form>
                    </ModalBody>
                </Modal>

                <Modal isOpen={modalYesterdayVisible}>
                    <ModalHeader>
                         Ishlab chiqarilgan mahsulotlar
                    </ModalHeader>
                    <ModalBody>
                        <form>
                            <input type={"date"} name={"date"} value={date} onChange={producedProducts}/>
                            {/*<button type={"button"} className={"btn btn-secondary float-sm-right"}*/}
                            {/*        onClick={producedProducts}>Chiqarilgan mahsulotlar*/}
                            {/*</button>*/}
                        </form>
                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Mahsulot</th>
                                <th>Miqdori (m2)</th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                yesterdayProd.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item[0]}</th>
                                        <th>{item[1]}</th>
                                    </tr>
                                )
                            }
                            <h6>Umumiy: {totalAmount} (m2)</h6>
                            <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                            </tbody>
                        </table>
                    </ModalBody>
                </Modal>


                <Modal isOpen={modalStockVisible}>
                    <ModalHeader>
                        Yangi mahsulot kiritish
                    </ModalHeader>
                    <ModalBody>
                        <form onSubmit={addStock}>
                            <label>Mahsulot: </label>
                            <input className={"form-control"} type={"text"} placeholder={"Mahsulot ismi"} required/><br/>


                            <button type={"button"} className={"btn btn-danger"} onClick={close}>Yopish</button>
                            <button style={{marginLeft: 10}} className={"btn btn-success"}>Qo'shish</button>
                        </form>
                    </ModalBody>
                </Modal>


                <div className={"row"}>
                    <div className={"col"}>
                        {/*<input type={"text"} placeholder={"Qidirish..."} name={"filterName"} onChange={event => filterChange(event,1)}/>*/}

                        <h2 style={{color:"blue"}}>Mahsulotlar</h2>

                        <button type={"button"} className={"btn btn-outline-warning float-sm-right"}
                                onClick={openModal}>+Mahsulot qo'shish
                        </button>

                        <button type={"button"} className={"btn btn-info float-sm-right"}
                                onClick={openProducts}>Chiqarilgan mahsulotlar
                        </button>

                        <button type={"button"} className={"btn btn-primary float-sm-left"}
                                onClick={openStockModal}>Yangi mahsulot kiritish
                        </button>


                        {/*<button type={"button"} className={"btn btn-info float-sm-right"}*/}
                        {/*        onClick={todayProducts}>Mahsulotlar bugun*/}
                        {/*</button>*/}


                        <table className={"table"}>
                            <thead className={"thead-dark"}>
                            <tr>
                                <th>#</th>
                                <th>Mahsulot</th>
                                <th>Miqdori (m2)</th>
                                <th>O'chirish</th>
                            </tr>
                            </thead>

                            <tbody>
                            {
                                products.map((item, index) =>
                                    <tr key={item.id}>
                                        <th>{index + 1}</th>
                                        <th>{item.name}</th>
                                        <th>{item.amount}</th>
                                        <th>
                                            <button className={"btn btn-danger"} onClick={() => deleteStock(item.id)}>X
                                            </button>
                                        </th>
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

export default Products