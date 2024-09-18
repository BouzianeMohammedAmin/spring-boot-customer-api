import { createStandaloneToast } from '@chakra-ui/react'

const { toast } = createStandaloneToast()

export const notification = (title , description , status )=>{

    toast({
        title: title,
        description: description,
        status: status,
        duration: 4000,
        isClosable: true,
    })
}