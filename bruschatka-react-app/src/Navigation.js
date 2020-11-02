import React from 'react';

import { NavLink } from 'react-router-dom';

const Navigation = () => {

    return (
        <div style={{backgroundColor:"black", marginBottom:20, paddingBottom:10}}>
            <NavLink style={{color:"white", paddingRight:20, fontSize:20}} to="/clients">Klientlar</NavLink>
            <NavLink style={{color:"white", paddingRight:20, fontSize:20}} to="/products">Mahsulotlar</NavLink>
            <NavLink style={{color:"white", paddingRight:20, fontSize:20}} to="/delivery">Dostavka</NavLink>
            <NavLink style={{color:"white", paddingRight:20, fontSize:20}} to="/archive">Arxiv</NavLink>
        </div>
    );
};

export default Navigation;