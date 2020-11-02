import React from "react"

const Pagination = ({postsPerPage, totalPosts, paginate, pageNumber, name}) => {
    const pageNumbers = [];

    for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
        pageNumbers.push(i)
    }


    return (
        <nav>
            <ul className={'pagination'}>
                {pageNumbers.map(number => (
                    <li key={number} className={pageNumber===number?"page-item active":"page-item"}>
                        <a onClick={() => paginate(number, name)}  className={'page-link'} >
                            {number}
                        </a>
                    </li>
                ))}
            </ul>
        </nav>
    )
};

export default Pagination