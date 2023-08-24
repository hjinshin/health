import React, {useMemo} from 'react';
import {useTable} from 'react-table';
import {COLUMNS} from '../columns';
import './Table.css'

function Table(props) {
    const columns = useMemo(() => COLUMNS, []);
    const data = useMemo(() => props.data, [props.data]);
    const tableInstance = useTable({
        columns,
        data
    })

    const {
        getTableProps, 
        getTableBodyProps, 
        headerGroups, 
        rows, 
        prepareRow,
    } = tableInstance;

    return (
        <table {...getTableProps()}>
            <thead>
                {
                    headerGroups.map((headerGroup) => (
                        <tr className='header' {...headerGroup.getHeaderGroupProps()}>
                            {
                                headerGroup.headers.map((column) => (
                                    <th {...column.getHeaderProps()}>
                                        {column.render('Header')}
                                    </th>
                                ))
                            }
                        </tr>                        
                    ))
                }
            </thead>
            <tbody {...getTableBodyProps()}>
                {
                    rows.map(row => {
                        prepareRow(row);
                        return (
                            <tr className='list'
                                {...row.getRowProps()}
                                onClick={()=>props.onSubmit(row.original.nickname)}
                                >
                                {
                                    row.cells.map((cell) => {
                                        return (
                                            <td {...cell.getCellProps()}>
                                                {cell.render('Cell')}
                                            </td>                                            
                                        )
                                    })
                                }
                            </tr>                            
                        )
                    })
                }

            </tbody>
        </table>
    );
}

export default Table;