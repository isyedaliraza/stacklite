import { Button, CloseButton, Dialog, Portal } from "@chakra-ui/react";
import { FaPlus } from "react-icons/fa";
import NewQuestionForm from "./NewQuestionForm";
import { useState } from "react";

const NewQuestionButton = () => {
	const [open, setOpen] = useState(false);

	const handleClose = () => setOpen(false);

	return (
		<Dialog.Root
			motionPreset="slide-in-bottom"
			open={open}
			onOpenChange={(e) => setOpen(e.open)}
		>
			<Dialog.Trigger asChild>
				<Button size="sm">
					<FaPlus /> Add New
				</Button>
			</Dialog.Trigger>
			<Portal>
				<Dialog.Backdrop />
				<Dialog.Positioner>
					<Dialog.Content>
						<Dialog.Header>
							<Dialog.Title>New Question</Dialog.Title>
						</Dialog.Header>
						<Dialog.Body>
							<NewQuestionForm handleClose={handleClose} />
						</Dialog.Body>
						<Dialog.CloseTrigger asChild>
							<CloseButton size="sm" />
						</Dialog.CloseTrigger>
					</Dialog.Content>
				</Dialog.Positioner>
			</Portal>
		</Dialog.Root>
	);
};

export default NewQuestionButton;
