import { AbsoluteCenter, Box, Spinner } from "@chakra-ui/react";

const QuestionsLoading = () => {
	return (
		<Box position="relative" w="100%" h="auto" bg="gray.100" borderRadius="lg">
			<AbsoluteCenter>
				<Spinner />
			</AbsoluteCenter>
		</Box>
	);
};

export default QuestionsLoading;
