
import {
    Button,
    Drawer, DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import React from "react";
import CreateCustomForm from "./CreateCustomForm.jsx";
import { IoIosPersonAdd } from "react-icons/io";
import { AiOutlineClose } from "react-icons/ai";
import UpdateCustomForm from "./UpdateCustomForm.jsx";





const CreateCustomerDrawer = ({fetchCustomers ,id, name , email , age , gender})=>{
    const { isOpen, onOpen, onClose } = useDisclosure()
    const btnRef = React.useRef()

    return(
        <>

        <Button
            colorScheme={"teal"}
            onClick={onOpen} >
                Update </Button>

            <Drawer
                isOpen={isOpen}
                placement='right'
                onClose={onClose}
                finalFocusRef={btnRef}
                size={"xl"}m
            >
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader >Update User</DrawerHeader>

                    <DrawerBody>
                        <UpdateCustomForm fetchCustomers = {fetchCustomers} id={id} name={name} email={email}
                                          age={age}  gender={gender}/>
                    </DrawerBody>

                    <DrawerFooter>
                        <Button
                            leftIcon={<AiOutlineClose/>}
                            onClick={onClose} >
                           Close </Button>

                    </DrawerFooter>
                </DrawerContent>
            </Drawer>

        </>
    ) ;
};

export  default  CreateCustomerDrawer ;