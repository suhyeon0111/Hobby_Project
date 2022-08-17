import { Button, Icon } from 'antd'
import React from 'react'

function HobbyStoryPage() {
    return (
        <div
            style={{
                textAlign: 'center',
                width: '100%'
            }}>
            <div
                style={{
                    width: '100%',
                    margin: '3rem auto'
                }}>
                <p
                    style={{
                        textAlign: 'left',
                        marginLeft: '27px',
                        fontSize: '16px'
                    }}>취미 스토리</p>
                    <hr style={{width: '90%', backgroundColor: '#D3BA9C'}} />
                    <a href='/hobbystory/upload'>
                    <Button shape='circle' style={{background: '#D3BA9C', width: '40px', height: '40px', position: 'absolute', right: 0, bottom: 0, margin: '0px 13px 100px 0px'}}>
                        <Icon type='plus' style={{color: '#fff', fontSize: '20px'}} />
                    </Button>
                    </a>
            </div>
        </div>
    )
}

export default HobbyStoryPage