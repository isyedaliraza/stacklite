import { EmptyState, VStack } from "@chakra-ui/react";
import { FaTimes } from "react-icons/fa";

const QuestionsError = ({ message }: { message: string }) => {
	return (
		<EmptyState.Root>
			<EmptyState.Content>
				<EmptyState.Indicator>
					<FaTimes color="red" />
				</EmptyState.Indicator>
				<VStack textAlign="center">
					<EmptyState.Title>Something went wrong!</EmptyState.Title>
					<EmptyState.Description>{message}</EmptyState.Description>
				</VStack>
			</EmptyState.Content>
		</EmptyState.Root>
	);
};

export default QuestionsError;
